// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.domain.schedule.batch;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mercedesbenz.sechub.commons.model.SecHubMessage;
import com.mercedesbenz.sechub.commons.model.SecHubMessageType;
import com.mercedesbenz.sechub.commons.model.SecHubMessagesList;
import com.mercedesbenz.sechub.commons.model.TrafficLight;
import com.mercedesbenz.sechub.commons.model.job.ExecutionResult;
import com.mercedesbenz.sechub.domain.schedule.ScheduleJobMarkerService;
import com.mercedesbenz.sechub.domain.schedule.SchedulerSecHubJobRuntimeData;
import com.mercedesbenz.sechub.domain.schedule.SchedulerSecHubJobRuntimeRegistry;
import com.mercedesbenz.sechub.domain.schedule.job.ScheduleSecHubJob;
import com.mercedesbenz.sechub.sharedkernel.LogConstants;
import com.mercedesbenz.sechub.sharedkernel.Step;
import com.mercedesbenz.sechub.sharedkernel.messaging.DomainMessage;
import com.mercedesbenz.sechub.sharedkernel.messaging.DomainMessageService;
import com.mercedesbenz.sechub.sharedkernel.messaging.DomainMessageSynchronousResult;
import com.mercedesbenz.sechub.sharedkernel.messaging.IsSendingAsyncMessage;
import com.mercedesbenz.sechub.sharedkernel.messaging.IsSendingSyncMessage;
import com.mercedesbenz.sechub.sharedkernel.messaging.JobMessage;
import com.mercedesbenz.sechub.sharedkernel.messaging.MessageDataKey;
import com.mercedesbenz.sechub.sharedkernel.messaging.MessageDataKeys;
import com.mercedesbenz.sechub.sharedkernel.messaging.MessageID;
import com.mercedesbenz.sechub.sharedkernel.usecases.other.UseCaseSystemHandlesSIGTERM;

/**
 * This component executes SecHub jobs in own worker threads (means
 * parallel/asynchronous) but the event handling inside the worker thread is
 * done synchronous to wait inside scheduler domain for scan results from other
 * domain (scan) - this is the reason for the naming.
 *
 * @author Albert Tregnaghi
 *
 */
@Component
public class SynchronSecHubJobExecutor {

    private static final String SECHUB_SCHEDULE_THREAD_PREFIX = "sechub-schedule:";

    private static final Logger LOG = LoggerFactory.getLogger(SynchronSecHubJobExecutor.class);

    @Autowired
    @Lazy
    DomainMessageService messageService;

    @Autowired
    SchedulerSecHubJobRuntimeRegistry runtimeRegistry;

    @Autowired
    SecHubJobSafeUpdater secHubJobSafeUpdater;

    @Autowired
    ScheduleJobMarkerService markerService;

    private boolean suspended;

    @IsSendingSyncMessage(MessageID.START_SCAN)
    public void execute(final ScheduleSecHubJob secHubJob) {
        SchedulerSecHubJobRuntimeData runtimeInfo = new SchedulerSecHubJobRuntimeData(secHubJob.getUUID());
        runtimeInfo.setExecutionUUID(UUID.randomUUID());
        if (suspended) {
            return;
        }
        // if not suspended, register
        runtimeRegistry.register(runtimeInfo);

        Thread scheduleWorkerThread = new Thread(() -> executeInsideThread(secHubJob), SECHUB_SCHEDULE_THREAD_PREFIX + secHubJob.getUUID() + "-schedule"); // we
                                                                                                                                                           // add
                                                                                                                                                           // -schedule
                                                                                                                                                           // to
                                                                                                                                                           // end,
                                                                                                                                                           // to
                                                                                                                                                           // have
                                                                                                                                                           // info
                                                                                                                                                           // in
                                                                                                                                                           // logging
                                                                                                                                                           // when
                                                                                                                                                           // thread
                                                                                                                                                           // name
                                                                                                                                                           // is
                                                                                                                                                           // reduced
        scheduleWorkerThread.start();
    }

    private void executeInsideThread(final ScheduleSecHubJob secHubJob) {
        SchedulerSecHubJobRuntimeData runtimeInfo = runtimeRegistry.fetchDataForSecHubJobUUID(secHubJob.getUUID());

        try {
            String secHubConfiguration = secHubJob.getJsonConfiguration();

            /* own thread so MDC.put necessary for logging */
            MDC.clear();
            MDC.put(LogConstants.MDC_SECHUB_JOB_UUID, runtimeInfo.getSecHubJobUUIDasString());
            MDC.put(LogConstants.MDC_SECHUB_EXECUTION_UUID, runtimeInfo.getExecutionUUIDAsString());
            MDC.put(LogConstants.MDC_SECHUB_PROJECT_ID, secHubJob.getProjectId());

            LOG.info("Executing sechub job: {}, execution uuid: {}", runtimeInfo.getSecHubJobUUIDasString(), runtimeInfo.getExecutionUUIDAsString());

            sendJobExecutionStartingEvent(secHubJob, runtimeInfo, secHubConfiguration);

            /* we send now a synchronous SCAN event */
            DomainMessage request = new DomainMessage(MessageID.START_SCAN);
            request.set(MessageDataKeys.SECHUB_EXECUTION_UUID, runtimeInfo.getExecutionUUID());
            request.set(MessageDataKeys.EXECUTED_BY, secHubJob.getOwner());

            request.set(MessageDataKeys.SECHUB_JOB_UUID, runtimeInfo.getSecHubJobUUID());
            request.set(MessageDataKeys.SECHUB_CONFIG, MessageDataKeys.SECHUB_CONFIG.getProvider().get(secHubConfiguration));

            /* wait for scan event result - synchron */
            DomainMessageSynchronousResult response = messageService.sendSynchron(request);

            if (response.getMessageId() == MessageID.SCAN_SUSPENDED) {
                sendJobSuspended(runtimeInfo, TrafficLight.OFF);
                return;
            }
            updateSecHubJob(runtimeInfo, response);

            sendJobDoneMessage(runtimeInfo, response);

        } catch (Exception e) {
            LOG.error("Error happend at job execution:" + e.getMessage(), e);

            markSechHubJobFailed(runtimeInfo);
            sendJobFailed(runtimeInfo, TrafficLight.OFF);

        } finally {
            runtimeRegistry.unregisterBySecHubJobUUID(secHubJob.getUUID());
            /* cleanup MDC */
            MDC.clear();
        }
    }

    /**
     * Suspends execution: will stop executing any new triggered job and also mark
     * the current running to be suspended.
     */
    @UseCaseSystemHandlesSIGTERM(@Step(number = 2, name = "Scheduler job executor suspends current jobs", description = "Scheduler instance is terminating. Will mark current running jobs of this instance as SUSPENDED"))
    public void suspend() {
        // mark this executor to no longer accept new entries
        suspended = true;

        // mark all current running jobs as suspended
        Set<UUID> secHubJobUUIDsToSuspend = runtimeRegistry.fetchAllSecHubJobUUIDs();
        markerService.markJobsAsSuspended(secHubJobUUIDsToSuspend);

        // info: after this, the execution state of these jobs will be SUSPENDED
        // The scan domain will inspect the suspension state of jobs and stop processing

    }

    @Deprecated
    /**
     * Only for integration tests! Will reset internal state to allow execution
     * again
     */
    public void resetSuspensionState() {
        suspended = false;
    }

    @IsSendingAsyncMessage(MessageID.JOB_EXECUTION_STARTING)
    private void sendJobExecutionStartingEvent(final ScheduleSecHubJob secHubJob, SchedulerSecHubJobRuntimeData uuids, String secHubConfiguration) {
        /* we send asynchronous an information event */
        DomainMessage jobExecRequest = new DomainMessage(MessageID.JOB_EXECUTION_STARTING);

        jobExecRequest.set(MessageDataKeys.SECHUB_EXECUTION_UUID, uuids.getExecutionUUID());
        jobExecRequest.set(MessageDataKeys.SECHUB_JOB_UUID, uuids.getSecHubJobUUID());
        jobExecRequest.set(MessageDataKeys.LOCAL_DATE_TIME_SINCE, secHubJob.getStarted());

        messageService.sendAsynchron(jobExecRequest);
    }

    private void sendJobDoneMessage(SchedulerSecHubJobRuntimeData uuids, DomainMessageSynchronousResult response) {
        LOG.debug("Will send job done message for: {}", uuids.getSecHubJobUUIDasString());

        String trafficLightAsString = response.get(MessageDataKeys.REPORT_TRAFFIC_LIGHT);

        sendJobDone(uuids, TrafficLight.fromString(trafficLightAsString));
    }

    private void markSechHubJobFailed(SchedulerSecHubJobRuntimeData uuids) {
        SecHubMessage jobFailedMessage = new SecHubMessage(SecHubMessageType.ERROR, "The job execution failed.");
        updateSecHubJob(uuids, ExecutionResult.FAILED, null, Arrays.asList(jobFailedMessage));

        LOG.info("marked job as failed - sechub job: {}, execution-uuid: {}", uuids.getSecHubJobUUIDasString(), uuids.getExecutionUUIDAsString());
    }

    private void updateSecHubJob(SchedulerSecHubJobRuntimeData uuids, DomainMessageSynchronousResult response) {
        ExecutionResult result;

        if (response.hasFailed()) {
            result = ExecutionResult.FAILED;
        } else {
            result = ExecutionResult.OK;
        }
        String trafficLightString = response.get(MessageDataKeys.REPORT_TRAFFIC_LIGHT);

        SecHubMessagesList messagesList = response.get(MessageDataKeys.REPORT_MESSAGES);
        List<SecHubMessage> messages = null;
        if (messagesList != null) {
            messages = messagesList.getSecHubMessages();
        }

        updateSecHubJob(uuids, result, trafficLightString, messages);
    }

    private void updateSecHubJob(SchedulerSecHubJobRuntimeData data, ExecutionResult result, String trafficLightString, List<SecHubMessage> messages) {
        secHubJobSafeUpdater.safeUpdateOfSecHubJob(data.getSecHubJobUUID(), result, trafficLightString, messages);
    }

    @IsSendingAsyncMessage(MessageID.JOB_DONE)
    private void sendJobDone(SchedulerSecHubJobRuntimeData data, TrafficLight trafficLight) {
        sendJobInfoWithTrafficLight(MessageDataKeys.JOB_DONE_DATA, data, MessageID.JOB_DONE, trafficLight);
    }

    @IsSendingAsyncMessage(MessageID.JOB_FAILED)
    private void sendJobFailed(SchedulerSecHubJobRuntimeData data, TrafficLight trafficLight) {
        sendJobInfoWithTrafficLight(MessageDataKeys.JOB_FAILED_DATA, data, MessageID.JOB_FAILED, trafficLight);
    }

    @IsSendingAsyncMessage(MessageID.JOB_SUSPENDED)
    @UseCaseSystemHandlesSIGTERM(@Step(number = 6, name = "Inform listeners", description = "Inform listeners about job suspension"))
    private void sendJobSuspended(SchedulerSecHubJobRuntimeData data, TrafficLight trafficLight) {
        sendJobInfoWithTrafficLight(MessageDataKeys.JOB_SUSPENDED_DATA, data, MessageID.JOB_SUSPENDED, trafficLight);
    }

    private void sendJobInfoWithTrafficLight(MessageDataKey<JobMessage> key, SchedulerSecHubJobRuntimeData data, MessageID id, TrafficLight trafficLight) {
        DomainMessage request = new DomainMessage(id);
        JobMessage message = createMessage(data);
        message.setTrafficLight(trafficLight);
        request.set(key, message);
        request.set(MessageDataKeys.SECHUB_EXECUTION_UUID, data.getExecutionUUID());

        messageService.sendAsynchron(request);
    }

    private JobMessage createMessage(SchedulerSecHubJobRuntimeData uuids) {
        JobMessage message = new JobMessage();
        message.setJobUUID(uuids.getSecHubJobUUID());
        message.setSince(LocalDateTime.now());
        return message;
    }

}
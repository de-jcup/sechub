package com.mercedesbenz.sechub.integrationtest.api;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mercedesbenz.sechub.commons.model.TrafficLight;

/**
 * A simple test object to simulate SecHubJobInfoForUser (which is not available
 * from classpath in integration tests)
 *
 */
public class TestSecHubJobInfoForUser {

    public UUID jobUUID;

    public String executedBy;

    public LocalDateTime created;
    public LocalDateTime started;

    public LocalDateTime ended;

    public String executionState;

    public String executionResult;

    public TrafficLight trafficLight;

}
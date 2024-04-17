// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.wrapper.prepare.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mercedesbenz.sechub.commons.pds.PDSDefaultParameterKeyConstants;
import com.mercedesbenz.sechub.commons.pds.PDSDefaultRuntimeKeyConstants;

@Component
public class PrepareWrapperEnvironment {

    /********************************/
    /* PDS common environment setup */
    /********************************/

    @Value("${" + PDSDefaultParameterKeyConstants.PARAM_KEY_PDS_SCAN_CONFIGURATION + "}")
    private String sechubConfigurationModelAsJson;

    @Value("${" + PDSDefaultRuntimeKeyConstants.RT_KEY_PDS_JOB_RESULT_FILE + "}")
    private String pdsResultFile;

    @Value("${" + PDSDefaultRuntimeKeyConstants.RT_KEY_PDS_JOB_USER_MESSAGES_FOLDER + "}")
    private String pdsUserMessagesFolder;

    @Value("${" + PDSDefaultRuntimeKeyConstants.RT_KEY_PDS_PREPARE_UPLOAD_FOLDER_DIRECTORY + "}")
    private String pdsPrepareUploadFolderDirectory;

    public String getSechubConfigurationModelAsJson() {
        return sechubConfigurationModelAsJson;
    }

    public String getPdsResultFile() {
        return pdsResultFile;
    }

    public String getPdsUserMessagesFolder() {
        return pdsUserMessagesFolder;
    }

    public String getPdsPrepareUploadFolderDirectory() {
        return pdsPrepareUploadFolderDirectory;
    }

}

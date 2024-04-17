// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.wrapper.prepare.moduls;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.mercedesbenz.sechub.commons.model.SecHubConfigurationModel;

@Service
public interface PrepareWrapperModule {

    boolean isAbleToPrepare(SecHubConfigurationModel model);

    void prepare(SecHubConfigurationModel model, String pdsPrepareUploadFolderDirectory) throws IOException;

    void cleanDirectory(String pdsPrepareUploadFolderDirectory) throws IOException;

}

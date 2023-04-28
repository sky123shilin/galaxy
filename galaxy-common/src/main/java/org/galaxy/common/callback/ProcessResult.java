package org.galaxy.common.callback;

import lombok.Builder;

@Builder
public class ProcessResult {

    private ProcessStatus status;

    private String messageId;

}

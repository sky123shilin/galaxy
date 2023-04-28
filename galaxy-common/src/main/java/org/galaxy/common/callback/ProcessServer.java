package org.galaxy.common.callback;

import org.galaxy.common.callback.Message.Message;

public class ProcessServer {

    public void sendMessage(Message<?> message, ProcessCallBack processCallBack){
        System.out.println("Begin Process");

        try {
            Thread.sleep(10000);
            System.out.println(message.toString());
            if(message.getPayload() instanceof String){
                throw new IllegalArgumentException("参数不正确");
            }
            processCallBack.onSuccess(ProcessResult.builder().messageId(message.getId()).status(ProcessStatus.PROCESS_SUCCESS).build());
        } catch (IllegalArgumentException | InterruptedException e){
            processCallBack.onException(e);
        }

        System.out.println("End Process");
    }
}

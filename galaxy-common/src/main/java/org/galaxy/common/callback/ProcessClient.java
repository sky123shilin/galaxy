package org.galaxy.common.callback;

import org.galaxy.common.callback.Message.Message;

import java.util.UUID;

public class ProcessClient {

    public static void main(String[] args) {
        ProcessServer processServer = new ProcessServer();

        Message<Object> message = Message.builder().id(UUID.randomUUID().toString()).payload("Hello World").build();

        processServer.sendMessage(message, new ProcessCallBack() {
            @Override
            public void onSuccess(ProcessResult var1) {
                System.out.println("处理成功");
            }

            @Override
            public void onException(Throwable var1) {
                System.out.println(var1.getMessage());
                var1.printStackTrace();
            }
        });

    }

}

package org.galaxy.common.callback.Message;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class Message<T> {

    private String id;

    private MessageHeaders headers;

    private T payload;

    public T getPayload(){
        return this.payload;
    }

    public MessageHeaders getHeaders(){
        return this.headers;
    }

    @Override
    public String toString() {
        return "[ MessageId:" + id + ", Payload: " + payload.toString() + "]";
    }
}

package org.galaxy.util.exception;

public class BusinessException extends RuntimeException {

    private final int code;

    private final String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BusinessException of(int code, String message) {
        return new BusinessException(code,message);
    }
}

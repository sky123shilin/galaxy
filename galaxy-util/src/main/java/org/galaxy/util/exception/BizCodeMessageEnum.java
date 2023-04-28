package org.galaxy.util.exception;

public enum BizCodeMessageEnum {

    SUCCESS(0,"success"),
    FAIL(1,"fail"),


    NONE(999999,"default")
    ;


    private final int code;
    private final String message;

    BizCodeMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package org.galaxy.util.exception;

public class WebException extends RuntimeException {

    private final BizCodeMessageEnum bizCodeMessageEnum;


    public WebException(BizCodeMessageEnum bizCodeMessageEnum) {
        super(bizCodeMessageEnum.getMessage());
        this.bizCodeMessageEnum = bizCodeMessageEnum;
    }

    public BizCodeMessageEnum getBizCodeMessageEnum() {
        return bizCodeMessageEnum;
    }

    public static WebException of(BizCodeMessageEnum bizCodeMessageEnum) {
        return new WebException(bizCodeMessageEnum);
    }
}

package org.galaxy.common.constants;

public enum BizCodeMessageEnum {

    SUCCESS("000000","成功"),

    FEIGN_NOT_FOUND("999998","Feign调用发生404 Not Found"),
    FAIL("999999","失败");
    // 成员变量
    private String message;
    private String bizCode;

    // 构造方法
    BizCodeMessageEnum(String bizCode, String message) {
        this.message = message;
        this.bizCode = bizCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBizCode(String bizCode){
        this.bizCode = bizCode;
    }

    public String getMessage() {
        return message;
    }


    public String getBizCode() {
        return bizCode;
    }

    public static BizCodeMessageEnum findEnumByName(String name) {
        for(BizCodeMessageEnum bizCodeMessageEnum : BizCodeMessageEnum.values()) {
            if(bizCodeMessageEnum.name().equals(name)) {
                return bizCodeMessageEnum;
            }
        }
        return BizCodeMessageEnum.FAIL;
    }

    public static BizCodeMessageEnum findEnumByBizCode(String bizCode){
        for(BizCodeMessageEnum bizCodeMessageEnum : BizCodeMessageEnum.values()) {
            if(bizCodeMessageEnum.getBizCode().equals(bizCode)) {
                return bizCodeMessageEnum;
            }
        }
        return BizCodeMessageEnum.FAIL;
    }

}

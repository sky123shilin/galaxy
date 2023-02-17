package org.galaxy.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.ToString;
import org.galaxy.common.constants.BizCodeMessageEnum;

import java.io.Serializable;

/**
 * 标准Rest接口返回对象
 * @param <T>
 */
@JsonPropertyOrder({ "bizCode", "message", "data" })
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@Data
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String bizCode;
    protected String message;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    protected T data;

    public RestResponse(){}

    public RestResponse(String bizCode, String message, T data) {
        super();
        this.bizCode = bizCode;
        this.message = message;
        this.data = data;
    }

    public RestResponse(BizCodeMessageEnum bizCodeMessageEnum, T data){
        this(bizCodeMessageEnum.getBizCode(),bizCodeMessageEnum.getMessage(),data);
    }

    public static <T> RestResponse<T> create(String bizCode, String message, T data) {
        return new RestResponse<T>(bizCode, message, data);
    }

    public static <T> RestResponse<T> create(BizCodeMessageEnum bizCodeMessageEnum, T data) {
        return create(bizCodeMessageEnum.getBizCode(),bizCodeMessageEnum.getMessage(),data);
    }

    public static <T> RestResponse<T> ok(T data){
        return new RestResponse<T>(BizCodeMessageEnum.SUCCESS, data);
    }

    public static <T> RestResponse<T> fail(T data){
        return new RestResponse<T>(BizCodeMessageEnum.FAIL, data);
    }

    public static <T> RestResponse<T> fail(){
        return fail(null);
    }
}

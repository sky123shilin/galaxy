package org.galaxy.web.config;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.galaxy.common.constants.BizCodeMessageEnum;
import org.galaxy.common.response.RestResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Feign自定义配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract
 */
@Configuration
public class FeignConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public Decoder feignDecoder(){
//        return new MyFeignDecoder();
//    }

    /**
     * 自定义Feign的Decoder，在发生decode404的时候，友好返回
     */
    public static class MyFeignDecoder implements Decoder{
        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            return new RestResponse<>(BizCodeMessageEnum.FEIGN_NOT_FOUND,"");
        }
    }
}

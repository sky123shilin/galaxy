package org.galaxy.util.exception;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * 异常解析器
 * 一般用来处理不同异常的输出或者处理
 */
@Slf4j
public class ExceptionParser {

    private static final String DEFAULT_MESSAGE = "服务器内部异常";
    private static final Map<Class<? extends Throwable>, Parser> CACHE = Maps.newHashMap();


    static {
        CACHE.put(BusinessException.class, e -> ((BusinessException) e).getMessage());
        CACHE.put(WebException.class, e -> ((WebException) e).getBizCodeMessageEnum().getMessage());
    }

    private ExceptionParser() {
        throw new UnsupportedOperationException();
    }

    static String parse(Exception e) {
        Class<? extends Throwable> klass = e.getClass();
        Parser parser = CACHE.get(klass);

        if (Objects.isNull(parser)) {
            if (log.isWarnEnabled()) {
                log.warn("异常类 : [{}] 未找到对应的解析器, 返回默认错误信息 : [{}]",
                        klass.getName(), DEFAULT_MESSAGE);
            }
            if (log.isErrorEnabled()) {
                log.error(String.format("未解析到异常类, 异常信息, 异常类名 : [%s]", e.getClass().getName()), e);
            }
            return DEFAULT_MESSAGE;
        }
        return parser.parse(e);
    }


    private interface Parser {
        String parse(Exception e);
    }
}

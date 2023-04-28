package org.galaxy.common.form;

import lombok.Data;
import org.galaxy.common.constants.Constants;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询通用Form，接受前端传来的值
 * 待定
 */
@Data
public class BasePageForm {

    // 当前页码
    private final Integer page = Constants.SYSTEM_INIT_PAGE;
    // 每页大小
    private final Integer size = Constants.SYSTEM_INIT_SIZE;

    private Map<String,String> sortMap;
    // 排序字段
    private final String sort;

    public Map<String,String> parseSort(){
        if(StringUtils.hasText(sort)){
            return null;
        }
        sortMap = new HashMap<>(10);
        // 解析sort，多个排序逗号隔开，且排序字段和排序方式空格隔开，即name asc,age desc
        return sortMap;
    }

    public boolean check(){
        //check sort

        //check page&size

        return Boolean.FALSE;
    }
}

package org.galaxy.util.core;

import java.util.Map;
import java.util.Objects;

/**
 * String处理工具类
 */
public class StringUtils {

    private StringUtils(){}


    /**
     * 格式化字符串
     * 这里的格式化，只是针对字符串中${xxx},将xxx和paramMap的key对应上，使用其paramMap的value来替换整个${xxx}
     * @param input        被格式化的字符串
     * @param map          所需参数
     * @return             返回格式化后的字符串
     */
    public static String format(String input, Map<String,String> map){
       return format(input,map,"${","}");
    }

    /**
     * 格式化字符串
     * 这里的格式化，只是针对字符串中${xxx},将xxx和paramMap的key对应上，使用其paramMap的value来替换整个${xxx}
     * @param input     被格式化的字符串
     * @param map       所需参数
     * @param left      变量左侧标志字符
     * @param right     变量右侧标志字符
     * @return          返回格式化后的字符串
     */
    public static String format(String input, Map<String,String> map, String left, String right){
        if(Objects.isNull(input)) return input;
        right = Objects.isNull(right) ? "" : right;
        left = Objects.isNull(left) ? "" : left;
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if(org.springframework.util.StringUtils.hasText(entry.getValue())){
                input = input.replace(left.concat(entry.getKey()).concat(right), entry.getValue());
            }
        }
        return input;
    }

}

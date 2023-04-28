package org.galaxy.common.json.fastjson;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSONObject.toJSONString(Object object, JSONWriter.Feature... features)
 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
 * WriteMapNullValue——–是否输出值为null的字段,默认为false
 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
 */
public class FastJson {

    private FastJson(){}

    /**
     * 初始化一个三元json
     */
    public static String of(String key1, String value1, String key2, String value2, String key3, String value3){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key1,value1);
        jsonObject.put(key2,value2);
        jsonObject.put(key3,value3);
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     * 初始化一个二元json
     */
    public static String of(String key1, String value1, String key2, String value2){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key1,value1);
        jsonObject.put(key2,value2);
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     * 使用json path从深度json中级联获取想要的值
     * @param obj json对象
     * @param expression 表达式
     * @return 返回值
     */
    public static Object eval(JSONObject obj, String expression){
        return JSONPath.eval(obj,expression);
    }

    /**
     * 将json字符串转成对象
     * @param json      json字符串
     * @param clazz     对象的class
     * @param <T>       对象的类型
     * @return          返回转换后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        return JSONObject.parseObject(json,clazz);
    }

    /**
     * 将json字符串转换成HashMap
     * @param json    json字符串
     * @param <K>     Map的key类型
     * @param <V>     Map的value类型
     * @return        返回一个map
     */
    public static <K,V> Map<K,V> jsonToMap(String json){
        return JSONObject.parseObject(json,
                new TypeReference<HashMap<K,V>>() {}
        );
    }

    /**
     * 将json字符串转换成List,前提是json是一个[]格式
     * @param json json字符串
     * @return 返回一个List
     * @param <E> List的泛型参数
     */
    public static <E> List<E> jsonToList(String json){
        return JSONObject.parseObject(json,
                new TypeReference<List<E>>() {}
        );
    }

}

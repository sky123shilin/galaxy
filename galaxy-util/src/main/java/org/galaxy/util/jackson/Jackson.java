package org.galaxy.util.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.*;

/**
 * Jackson处理类
 */
public class Jackson {
    private static final ThreadLocal<ObjectMapper> om = new ThreadLocal<ObjectMapper>() {
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper;
        }
    };

    private Jackson() {
    }

    public static ObjectMapper getObjectMapper() {
        return om.get();
    }

    public static String checkNullOrDefaultByString(JsonNode jsonNode, String ... propertyKeys){
        JsonNode handleNode = checkNullOrDefaultByJsonNode(jsonNode,propertyKeys);
        if(handleNode != null){
            return handleNode.toString().replace("\"","");
        }
        return null;
    }

    public static JsonNode checkNullOrDefaultByJsonNode(JsonNode jsonNode, String ... propertyKeys){
        return checkNullOrDefaultByJsonNode(jsonNode,Arrays.asList(propertyKeys));
    }

    public static JsonNode checkNullOrDefaultByJsonNode(JsonNode jsonNode, List<String> propertyKeys){
        if(Objects.isNull(jsonNode)) return null;
        for(String propertyKey : propertyKeys){
            if(jsonNode instanceof ArrayNode){
                if(jsonNode.get(0) == null){
                    return null;
                }
                jsonNode = jsonNode.get(0).get(propertyKey);
            }else if(jsonNode instanceof ObjectNode){
                jsonNode = jsonNode.get(propertyKey);
            }else{
                break;
            }
        }
        return jsonNode;
    }

    /**
     * 将json字符串转换成Jackson的JsonNode，用来可以快速处理json
     * @param json   json字符串
     * @return       返回一个转换后的JsonNode
     */
    public static JsonNode convertJsonToNode(String json) {
        try{
            return getObjectMapper().readTree(json);
        } catch (JacksonException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转成json字符串
     * @param object  要转成json的对象
     * @return        返回json字符串或空
     */
    public static String convertObjectToJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转成对象
     * @param json      json字符串
     * @param clazz     对象的class
     * @param <T>       对象的类型
     * @return          返回转换后的对象
     */
    public static <T> T convertJsonToObject(String json, Class<T> clazz){
        try{
            return getObjectMapper().reader().forType(clazz).readValue(json);
        } catch (JacksonException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换成HashMap
     * @param json    json字符串
     * @param <K>     Map的key类型
     * @param <V>     Map的value类型
     * @return        返回一个map
     */
    public static <K,V> Map<K,V> convertJsonToMap(String json){
        try {
            return getObjectMapper().readValue(json, new TypeReference<HashMap<K,V>>() {});
        } catch (IOException e) {
            return null;
        }
    }

}

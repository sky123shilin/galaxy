package org.galaxy.common.json.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.galaxy.common.json.jackson.bean.User;

import java.io.IOException;
import java.util.*;

/**
 * Jackson处理类
 */
public class Jackson {

    private static final ThreadLocal<ObjectMapper> om = ThreadLocal.withInitial(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    });

    private Jackson() {}

    public static ObjectMapper getObjectMapper() {
        return om.get();
    }

    /**
     * 创建一个空的ObjectNode，用来初始化json  {}
     */
    public static ObjectNode getObjectNode(){
        return getObjectMapper().createObjectNode();
    }

    /**
     * 创建一个空的ArrayNode，用来初始化json []
     */
    public static ArrayNode getArrayNode(){
        return getObjectMapper().createArrayNode();
    }

    /**
     * 从Jackson的JsonNode中递归获取某个属性值
     * @param jsonNode Jackson的JsonNode
     * @param propertyKeys 某个属性或多个属性（属性之间是上下级关系，可递归）
     * @return 返回这个属性对应的value
     */
    public static String getPropertyValueByRecursive(JsonNode jsonNode, String ... propertyKeys){
        JsonNode handleNode = getJsonNodeByRecursive(jsonNode,propertyKeys);
        if(handleNode != null){
            return handleNode.toString().replace("\"","");
        }
        return null;
    }

    /**
     * 从Jackson的JsonNode中递归获取某个属性对应的JsonNode
     * @param jsonNode Jackson的JsonNode
     * @param propertyKeys  某个属性
     * @return 返回这个属性对应的JsonNode
     */
    public static JsonNode getJsonNodeByRecursive(JsonNode jsonNode, String ... propertyKeys){
        return getJsonNodeByRecursive(jsonNode,Arrays.asList(propertyKeys));
    }

    /**
     * 从Jackson的JsonNode中递归获取某个属性对应的JsonNode
     * @param jsonNode Jackson的JsonNode
     * @param propertyKeys  某个属性
     * @return 返回这个属性对应的JsonNode
     */
    public static JsonNode getJsonNodeByRecursive(JsonNode jsonNode, List<String> propertyKeys){
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
    public static JsonNode jsonToNode(String json) {
        try{
            return getObjectMapper().readTree(json);
        } catch (JacksonException e) {
            return null;
        }
    }

    /**
     * 将对象转成json字符串
     * @param object  要转成json的对象
     * @return        返回json字符串或空
     */
    public static String objectToJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 将对象转成Jackson的JsonNode，用来可以快速处理json
     * @param object 待转换的对象
     * @return 返回 JsonNode
     */
    public static JsonNode objectToNode(Object object){
        return getObjectMapper().convertValue(object, JsonNode.class);
    }

    /**
     * 将json字符串转成对象
     * @param json      json字符串
     * @param clazz     对象的class
     * @param <T>       对象的类型
     * @return          返回转换后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        try{
            return getObjectMapper().reader().forType(clazz).readValue(json);
        } catch (JacksonException e) {
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
    public static <K,V> Map<K,V> jsonToMap(String json){
        try {
            return getObjectMapper().readValue(
                    json, new TypeReference<HashMap<K,V>>() {}
            );
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    /**
     * 将json字符串转换成List,前提是json是一个[]格式
     * @param json json字符串
     * @return 返回一个List
     * @param <E> List的泛型参数
     */
    public static <E> List<E> jsonToList(String json){
        try {
            return getObjectMapper().readValue(
                    json, new TypeReference<ArrayList<E>>() {}
            );
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    /**
     * 将对象转换成HashMap
     * @param object  对象
     */
    public static <K,V> Map<K,V> objectToMap(Object object){
        try {
            return getObjectMapper().readValue(
                    getObjectMapper().writeValueAsString(object), new TypeReference<HashMap<K,V>>() {}
            );
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 初始化一个三元json
     */
    public static String of(String key1, String value1, String key2, String value2, String key3, String value3){
        return getObjectNode().put(key1,value1).put(key2,value2).put(key3,value3).toString();
    }

    /**
     * 初始化一个二元json
     */
    public static String of(String key1, String value1, String key2, String value2){
        return getObjectNode().put(key1,value1).put(key2,value2).toString();
    }

    /**
     * 将JsonNode进行扁平化处理，变成一个Map
     * @param parentPath  共有前缀
     * @param parentJsonNode 需要扁平化的Json
     * @return 返回扁平化的Map
     */
    public static Map<String, Object> flatJsonToMap(String parentPath, JsonNode parentJsonNode){
        Map<String, Object> map = new HashMap<>();
        Iterator<String> sIterator = parentJsonNode.fieldNames();
        while(sIterator.hasNext()) {
            String key = sIterator.next();
            JsonNode jsonNode = parentJsonNode.get(key);
            if(jsonNode.isArray()) {
                for (int i = 0; i < jsonNode.size(); i++) {
                    if (null != jsonNode.get(0)) {
                        Iterator<String> grid = jsonNode.get(0).fieldNames();
                        while(grid.hasNext()) {
                            String child = grid.next();
                            JsonNode jn = jsonNode.get(0).get(child);
                            String mapKey = key + "." + child;
                            if (jn.isDouble()) {
                                if (map.containsKey(mapKey)) {
                                    map.put(mapKey, (Double)(map.get(mapKey)) + jn.asDouble());
                                } else {
                                    map.put(mapKey, jn.asDouble());
                                }
                            } else if (jn.isFloat()) {
                                if (map.containsKey(mapKey)) {
                                    map.put(mapKey, (Float)(map.get(mapKey)) + jn.asDouble());
                                } else {
                                    map.put(mapKey, jn.asDouble());
                                }
                            } else if (jn.isInt()) {
                                if (map.containsKey(mapKey)) {
                                    map.put(mapKey, (Integer)(map.get(mapKey)) + jn.asInt());
                                } else {
                                    map.put(mapKey, jn.asInt());
                                }
                            } else {
                                if (map.containsKey(mapKey)) {
                                    map.put(mapKey, map.get(mapKey) + "," + jn.asText());
                                } else {
                                    map.put(mapKey, jn.asText());
                                }
                            }
                        }
                    }
                }
                map.put(key, jsonNode.toString());
            }
            else if(jsonNode.isValueNode()) {
                map.put(parentPath + "." + key, jsonNode.asText());
            }
            else if(jsonNode.isObject()) {
                flatJsonToMap( parentPath + "." + key, jsonNode);
            }
        }
        return map;
    }

}

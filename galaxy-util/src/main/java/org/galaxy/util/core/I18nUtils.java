package org.galaxy.util.core;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 国际化的工具类，针对spring mvc环境
 */
public class I18nUtils {

    private static final String LOCALE_ZH_CN = "zh_cn"; //中文简体
    private static final String LOCALE_ZH_TW = "zh_tw"; //中文繁体
    private static final String LOCALE_EN_US = "en_us"; //英语

    private static Properties prop = null;

    static{
        try {
            String i18n = "zh_cn";
            String file = MessageFormat.format("i18n/message{0}.properties", i18n);
            Resource resource = new ClassPathResource(file);
            EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
            prop = PropertiesLoaderUtils.loadProperties(encodedResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadI18nProp(){
        return prop;
    }


    public static String get(String key) {
        return loadI18nProp().getProperty(key);
    }


    public static Map<String,String> multiGet(String... keys) {
        Map<String,String> map = new HashMap<>();
        Properties prop = loadI18nProp();
        if (keys != null && keys.length > 0) {
            for (String key: keys) {
                map.put(key, prop.getProperty(key));
            }
        } else {
            for (String key: prop.stringPropertyNames()) {
                map.put(key, prop.getProperty(key));
            }
        }
        return map;
    }
}

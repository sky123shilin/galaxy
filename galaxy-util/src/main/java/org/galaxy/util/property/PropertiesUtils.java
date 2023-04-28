package org.galaxy.util.property;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Properties工具类
 */
public final class PropertiesUtils {

    private PropertiesUtils(){}

    /**
     * 加载Properties
     * @param propertyFileName Properties 文件全路径
     * @return 返回Properties对象
     */
    public static Properties loadProp(String propertyFileName) {
        InputStream in = null;
        try {
            File file = new File(propertyFileName);
            if (!file.exists()) {
                return null;
            }
            URL url = new File(propertyFileName).toURI().toURL();
            in = Files.newInputStream(Paths.get(url.getPath()));
            Properties prop = new Properties();
            prop.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 类路径资源加载properties
     * @param resource 类路径资源对象
     * @return 返回Properties对象
     */
    public static Properties loadProp(ClassPathResource resource) {
        InputStream in = null;
        try {
            if(!resource.exists()){
                return null;
            }
            Properties prop = new Properties();
            in = resource.getInputStream();
            prop.load(in);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

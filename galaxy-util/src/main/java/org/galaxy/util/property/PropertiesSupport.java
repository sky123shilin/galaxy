package org.galaxy.util.property;

import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Properties支持工具类
 */
public final class PropertiesSupport {

    private static final String PROPERTIES_RESOURCE_LOCATION = "xxx.properties";
    private static final Properties localProperties = new Properties();

    private PropertiesSupport() {}

    public static void setProperty(String key, @Nullable String value) {
        if (value != null) {
            localProperties.setProperty(key, value);
        } else {
            localProperties.remove(key);
        }

    }

    @Nullable
    public static String getProperty(String key) {
        String value = localProperties.getProperty(key);
        if (value == null) {
            try {
                value = System.getProperty(key);
            } catch (Throwable var3) {
                System.err.println("Could not retrieve system property '" + key + "': " + var3);
            }
        }

        return value;
    }

    public static void setFlag(String key) {
        localProperties.put(key, Boolean.TRUE.toString());
    }

    public static boolean getFlag(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    static {
        try {
            ClassLoader cl = PropertiesSupport.class.getClassLoader();
            URL url = cl != null ? cl.getResource(PROPERTIES_RESOURCE_LOCATION) : ClassLoader.getSystemResource(PROPERTIES_RESOURCE_LOCATION);
            if (url != null) {
                InputStream is = url.openStream();
                Throwable var3 = null;

                try {
                    localProperties.load(is);
                } catch (Throwable var13) {
                    var3 = var13;
                    throw var13;
                } finally {
                    if (is != null) {
                        if (var3 != null) {
                            try {
                                is.close();
                            } catch (Throwable var12) {
                                var3.addSuppressed(var12);
                            }
                        } else {
                            is.close();
                        }
                    }

                }
            }
        } catch (IOException var15) {
            System.err.println("Could not load 'spring.properties' file from local classpath: " + var15);
        }

    }

}

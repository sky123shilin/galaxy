package org.galaxy.util.core;

import com.google.common.collect.ImmutableList;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

/**
 * 获取环境信息的工具类
 */
public final class ProfileUtils {

    private final static String DEFAULT_ENV_PROFILE = "dev";
    private final static List<String> envProfileList;

    static{
        envProfileList = ImmutableList.of("dev", "test", "uat", "stg","prod");// 不可变集合
    }

    private ProfileUtils(){}

    public static String[] getActiveProfiles(){
        return getActiveProfiles(SpringContextHolder.getEnvironment());
    }

    public static String[] getActiveProfiles(Environment env){
        String[] profiles = env.getActiveProfiles();
        if(profiles.length == 0){
            return env.getDefaultProfiles();
        }
        return profiles;
    }

    public static String getActiveProfile(){
        return getActiveProfile(SpringContextHolder.getEnvironment());
    }

    public static String getActiveProfile(Environment env){
        String[] profiles = getActiveProfiles(env);
        return Arrays.stream(profiles).filter(envProfileList::contains).findFirst().orElse(DEFAULT_ENV_PROFILE);
    }
}

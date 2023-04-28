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
    private final static List<String> profileList;

    static{
        // 不可变集合
        profileList = ImmutableList.of(
                "dev", "test", "uat", "stg","prod"
        );
    }

    private ProfileUtils(){}

    /**
     * 获取当前环境的激活的Profile数组
     */
    public static String[] getActiveProfiles(){
        return getActiveProfiles(SpringContextHolder.getEnvironment());
    }

    /**
     * 基于Spring Environment获取激活的Profile数组
     * @param env Spring Environment对象
     * @return
     */
    public static String[] getActiveProfiles(Environment env){
        String[] profiles = env.getActiveProfiles();
        if(profiles.length == 0){
            return env.getDefaultProfiles();
        }
        return profiles;
    }

    /**
     * 获取当前环境的激活的Profile（基于上述集合里）
     */
    public static String getActiveProfile(){
        return getActiveProfile(SpringContextHolder.getEnvironment());
    }

    /**
     * 基于Spring Environment获取激活的Profile（基于上述集合里）
     * @param env Spring Environment对象
     */
    public static String getActiveProfile(Environment env){
        String[] profiles = getActiveProfiles(env);
        return Arrays.stream(profiles).filter(profileList::contains).findFirst().orElse(DEFAULT_ENV_PROFILE);
    }
}

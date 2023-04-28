package org.galaxy.common.executor;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadFactory;

/**
 * 用于线程池的ThreadFactory
 */
public class CustomizableThreadFactory implements ThreadFactory {

    private CustomizableThreadCreator customizableThreadCreator;

    public CustomizableThreadFactory(String prefix){
        this.customizableThreadCreator = new CustomizableThreadCreator(prefix);
    }

    public CustomizableThreadFactory(CustomizableThreadCreator customizableThreadCreator){
        this.customizableThreadCreator = customizableThreadCreator;
    }

    public void setCustomizableThreadCreator(CustomizableThreadCreator customizableThreadCreator1){
        this.customizableThreadCreator = customizableThreadCreator1;
    }

    @Override
    public Thread newThread(@Nullable Runnable r) {
        return customizableThreadCreator.createThread(r);
    }
}

package org.galaxy.common.callback;

/**
 * 回调的通用方式
 * 后续可参考这个简单示例
 */
public interface ProcessCallBack {

    void onSuccess(ProcessResult var1);

    void onException(Throwable var1);

}

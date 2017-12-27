package com.citylinkdata.yongzhou.http;

/**
 * Created by Dell on 2017/10/24.
 */

public interface ReqCallBack<T> {
    void onComplete();

    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);


}

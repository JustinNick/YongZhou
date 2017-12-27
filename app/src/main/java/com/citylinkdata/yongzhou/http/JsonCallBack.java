package com.citylinkdata.yongzhou.http;

/**
 * Created by Dell on 2017/10/24.
 */

public interface JsonCallBack {

    void Success(String content);

    void onReqFailed(String errorMsg);

    void onComplete();
}

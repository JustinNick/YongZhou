package com.citylinkdata.yongzhou.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Dell on 2017/10/24.
 */

public class HttpManager {


    public HttpManager(Context context) {

    }

    public HttpManager() {

    }

    /**
     * 不带参数get请求(显示加载栏)
     *
     * @param url      请求地址
     * @param cls      解析类型
     * @param callBack 回调接口
     * @param <T>      泛型
     */
    public <T> void asyncHttpGet(String url, final Class<T> cls, ReqCallBack callBack) {
        asyncHttpGet(url, null, cls, callBack);
    }

    /**
     * 带参数get请求(显示加载栏)
     *
     * @param url      请求地址
     * @param params   參數
     * @param cls      解析类型 传null则只走getJson,不走sucess方法
     * @param callBack 回调接口
     * @param <T>      泛型
     */
    public <T> void asyncHttpGet(String url, Map<String, String> params, final Class<T> cls, ReqCallBack callBack) {
        asyncHttpGet(url, params, cls, callBack, null);
    }


    public <T> void asyncHttpGet(String url, JsonCallBack jsonCallBack) {
        asyncHttpGet(url, null, jsonCallBack);
    }

    public <T> void asyncHttpGet(String url, Map<String, String> params, JsonCallBack jsonCallBack) {
        asyncHttpGet(url, params, null, null, jsonCallBack);
    }


    /**
     *  带参数get请求(显示加载栏)
     * @param url            请求地址
     * @param params         參數
     * @param cls             解析类型
     * @param callBack
    回调接口
     * @param jsonCallBack  json回调接口
     * @param <T>
     */

    /**
     * 带参数get请求(显示加载栏)
     *
     * @param url          请求地址
     * @param params       參數
     * @param cls          解析类型
     * @param callBack     解析回调
     * @param jsonCallBack json回调接口
     * @param <T>
     */
    public <T> void asyncHttpGet(String url, Map<String, String> params, final Class<T> cls, ReqCallBack callBack, JsonCallBack jsonCallBack) {
        asyncHttp(url, params, HttpHelp.GET, cls, callBack, jsonCallBack);
    }


    /**
     * 带参数post请求(不显示加载栏)
     *
     * @param url      请求地址
     * @param cls      解析类型
     * @param callBack 回调接口
     * @param <T>      泛型
     */
    public <T> void asyncHttpPost(Context context, String url, Map<String, String> params, final Class<T> cls, ReqCallBack callBack) {
        asyncHttp(url, params, HttpHelp.POST, cls, callBack, null);
    }

    /**
     * 带参数post请求(显示加载栏)
     *
     * @param url      请求地址
     * @param cls      解析类型
     * @param callBack 回调接口
     * @param <T>      泛型
     */
    public <T> void asyncHttpPost(String url, Map<String, String> params, final Class<T> cls, ReqCallBack callBack) {
        asyncHttpPost(url, params, cls, callBack, null);
    }


    /**
     * 带参数post请求(显示加载栏)
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param jsonCallBack Json回调
     * @param <T>
     */
    public <T> void asyncHttpPost(String url, Map<String, String> params, JsonCallBack jsonCallBack) {

        asyncHttpPost(url, params, null, null, jsonCallBack);
    }


    /**
     * 带参数post请求(显示加载栏)
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param cls          解析类型
     * @param callBack     解析模型回调
     * @param jsonCallBack Json回调
     * @param <T>
     */
    public <T> void asyncHttpPost(String url, Map<String, String> params, final Class<T> cls, ReqCallBack callBack, JsonCallBack jsonCallBack) {

        asyncHttp(url, params, HttpHelp.POST, cls, callBack, jsonCallBack);
    }


    /**
     * 请求网络调用方法
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param httpType     请求类型
     * @param cls          解析类型
     * @param reqCallBack  解析模型回调
     * @param jsonCallBack Json回调
     * @param <T>
     */
    public <T> void asyncHttp(String url, Map<String, String> params, int httpType, final Class<T> cls, ReqCallBack reqCallBack, JsonCallBack jsonCallBack) {
        HttpHelp httpHelp = HttpHelp.getInstance();

//        httpHelp.setCls(cls);
//
//            httpHelp.setReqCallBack(reqCallBack);
//
//
//            httpHelp.setJsonCallBack(jsonCallBack);

        httpHelp.requestAnsyc(url, params, httpType, cls, reqCallBack, jsonCallBack, null);
    }


    /**
     * 请求网络调用方法
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param cls         解析类型
     * @param reqCallBack 解析模型回调
     * @param <T>
     */
    public <T> void upLoadFile(String url, Map<String, Object> params, final Class<T> cls, ReqCallBack reqCallBack) {
        upLoadFile(url, params, cls, reqCallBack, null);
    }

    /**
     * 请求网络调用方法
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param cls          解析类型
     * @param reqCallBack  解析模型回调
     * @param jsonCallBack Json回调
     * @param <T>
     */
    public <T> void upLoadFile(String url, Map<String, Object> params, final Class<T> cls, ReqCallBack reqCallBack, JsonCallBack jsonCallBack) {

        upLoadFile(url, params, cls, reqCallBack, jsonCallBack, null);
    }


    /**
     * 请求网络调用方法
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param cls          解析类型
     * @param reqCallBack  解析模型回调
     * @param jsonCallBack Json回调
     * @param <T>
     */
    public <T> void upLoadFile(String url, Map<String, Object> params, final Class<T> cls, ReqCallBack reqCallBack, JsonCallBack jsonCallBack, HttpHelp.ReqProgressCallBack progressCallBack) {
        HttpHelp httpHelp = HttpHelp.getInstance();
        if (progressCallBack == null)
            httpHelp.requestAnsyc(url, params, HttpHelp.UPLOAD_FILES, cls, reqCallBack, jsonCallBack, progressCallBack);
        else
            httpHelp.requestAnsyc(url, params, HttpHelp.UPLOAD_FILES_PROGRESS, cls, reqCallBack, jsonCallBack, progressCallBack);

    }


}

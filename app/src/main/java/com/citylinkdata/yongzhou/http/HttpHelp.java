package com.citylinkdata.yongzhou.http;

import android.os.Handler;

import com.citylinkdata.yongzhou.BaseApplication;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.NetUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okio.Buffer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by Liqing on 2017/10/24.
 */

public class HttpHelp<T> {
    public static final String JSON_ERROR = "Json格式不正确";
    public static final String NO_INTERNET_CONNECT = "未检测到网络";

    public static final int GET = 0;
    public static final int POST = 1;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final String TAG = HttpHelp.class.getSimpleName();
    private static final String BASE_URL = "http://xxx.com/openapi";//请求接口根地址
    private static volatile HttpHelp mInstance;//单利引用
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    public static final int UPLOAD_FILES = 3;
    public static final int UPLOAD_FILES_PROGRESS = 4;
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信


    private Gson gson;


    /**
     * 初始化RequestManager
     */
    public HttpHelp() {

        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler();
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static HttpHelp getInstance() {
        HttpHelp inst = mInstance;
        if (inst == null) {
            synchronized (HttpHelp.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new HttpHelp();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }


    /**
     * Okhttp请求网络数据（getOrPost)
     *
     * @param url      服务器地址
     * @param params   请求参数
     * @param cls      解析对象
     * @param type     请求类型 get/post
     * @param callBack 回调接口
     * @param <T>      泛型
     */
    Request request;

    public <T> void requestAnsyc(String url, Map<String, Object> params, int type, Class<T> cls, ReqCallBack reqCallBack, JsonCallBack jsonCallBack, ReqProgressCallBack progressCallBack) {

        if (!NetUtils.isConnected(BaseApplication.getInstance())) {
            onCmpleteCallBack(reqCallBack, jsonCallBack);
            failedCallBack(NO_INTERNET_CONNECT, reqCallBack, jsonCallBack);
            return;
        }
//        if (url == null) {
//            dismissDailog();
//            callBack.onFailure(context, URL_NULL);
//            return;
//        }

        if (type == GET) {
            request = new Request.Builder().url(getGetUrl(url, params)).build();
        } else if (type == POST) {
            if(params == null){
                request = new Request.Builder().url(url).post(getRequestBody(params)).addHeader("content-type", "application/json").addHeader("Content-Length","0").build();
            }
            request = new Request.Builder().url(url).post(getRequestBody(params)).addHeader("content-type", "application/json").build();
        } else if (type == UPLOAD_FILES) {
            request = new Request.Builder().url(url).post(getFileRequestBody(params)).build();
        } else if (type == UPLOAD_FILES_PROGRESS) {
            //创建Request
            request = new Request.Builder().url(url).post(getFileProgressRequestBody(params, progressCallBack)).build();
        }


        ansycCall(request, cls, reqCallBack, jsonCallBack);

    }




    /**
     * 执行异步网络请求（getOrPost)
     *
     * @param <T>          泛型
     * @param request      请求参数
     * @param cls          对象
     * @param reqCallBack
     * @param jsonCallBack
     */
    private <T> void ansycCall(Request request, final Class<T> cls, final ReqCallBack reqCallBack, final JsonCallBack jsonCallBack) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onCmpleteCallBack(reqCallBack, jsonCallBack);
                L.e("onFailure==" + e.toString());
                failedCallBack("访问失败", reqCallBack, jsonCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onCmpleteCallBack(reqCallBack, jsonCallBack);
                if (response.isSuccessful()) {
                    String content = response.body().string();
                    L.e("response ----->" + content);
                    successCallBack(content, cls, reqCallBack, jsonCallBack);
                } else {
                    failedCallBack("服务器错误" + response.code(), reqCallBack, jsonCallBack);
                }
            }
        });
    }


    /**
     * 为get请求添加参数,直接生成请求url
     *
     * @return
     */
    private String getGetUrl(String url, Map<String, Object> par) {
        if (par == null) {
            return url;
        }
        if (par.size() == 0) {
            return url;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.append("?");
        int pos = 0;
        for (String key : par.keySet()) {
            stringBuffer.append(key + "=" + par.get(key));
            if (pos < par.size() - 1) {
                stringBuffer.append("&");
            }
            pos++;
        }
        return stringBuffer.toString();
    }


    /**
     * post添加请求参数(form请求）
     *
     * @param params
     * @return
     */
    private RequestBody getRequestBody(Map<String, Object> params) {

        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        if (params == null) {
            params = new HashMap<String, Object>();
        }
        for (String key : params.keySet()) {
            formBodyBuilder.add(key, params.get(key) == null ? "" : params.get(key).toString());
        }
        RequestBody formBody = formBodyBuilder.build();

        return formBody;

    }


    /**
     * 统一同意处理成功信息
     *
     * @param <T>
     * @param content 回传数据
     * @param cls
     */
    private <T> void successCallBack(final String content, final Class<T> cls, final ReqCallBack reqCallBack, final JsonCallBack jsonCallBack) {

        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (jsonCallBack != null) {
                    jsonCallBack.Success(content);
                }
                if (reqCallBack != null) {

                    try {
                        JSONObject json = new JSONObject(content);
                        boolean success = json.getBoolean("success");


                        if (success) {
                            if (cls != null) {
                                if (gson == null) {
                                    gson = new Gson();
                                }
                                final T result = (T) gson.fromJson(content, cls);
                                reqCallBack.onReqSuccess(result);
                            } else {
                                reqCallBack.onReqSuccess(content);
                            }
                        } else {
                            failedCallBack(json.getString("message"), reqCallBack, jsonCallBack);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        failedCallBack(JSON_ERROR, reqCallBack, jsonCallBack);
                    }


                }
            }
        });


    }


    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack reqCallBack, final JsonCallBack jsonCallBack) {

        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (reqCallBack != null) {
                    reqCallBack.onReqFailed(errorMsg);

                }
                if (jsonCallBack != null) {
                    jsonCallBack.onReqFailed(errorMsg);
                }
            }
        });
    }

    private void onCmpleteCallBack(final ReqCallBack reqCallBack, final JsonCallBack jsonCallBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (reqCallBack != null) {
                    reqCallBack.onComplete();
                }

                if (jsonCallBack != null) {
                    jsonCallBack.onComplete();
                }
            }
        });

    }



    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");

    /**
     *
     * 带文件 带参数 带进度条请求的body
     * @param params 参数
     * @param callBack
     * @return
     */
    private RequestBody getFileProgressRequestBody(Map<String, Object> params, final ReqProgressCallBack callBack) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        //追加参数
        for (String key : params.keySet()) {
            Object object = params.get(key);
            if (!(object instanceof File)) {
                builder.addFormDataPart(key, object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, callBack));
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        return body;
    }

    /**
     * 带文件带参数，不带进度条上传文件body
     * @param params
     * @return
     */
    private RequestBody getFileRequestBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        //追加参数
        for (String key : params.keySet()) {
            Object object = params.get(key);
            if (!(object instanceof File)) {
                builder.addFormDataPart(key, object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        return body;
    }


    /**
     * 创建带进度的RequestBody
     *
     * @param contentType MediaType
     * @param file        准备上传的文件
     * @param callBack    回调
     * @param <T>
     * @return
     */
    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file, final ReqProgressCallBack callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        L.e(TAG, "current------>" + current);
                        progressCallBack(remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public interface ReqProgressCallBack {
        /**
         * 响应进度更新
         */
        void onProgress(long total, long current);
    }

    /**
     * 统一处理进度信息
     *
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    if (callBack != null)
                        callBack.onProgress(total, current);
                }
            }
        });
    }


}

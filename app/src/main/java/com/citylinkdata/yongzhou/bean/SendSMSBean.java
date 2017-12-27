package com.citylinkdata.yongzhou.bean;

/**
 * Created by dell on 2017/11/28.
 */

public class SendSMSBean {

    /**
     * success : false
     * message : 短信发送失败：手机号未注册
     * data : null
     */

    private boolean success;
    private String message;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

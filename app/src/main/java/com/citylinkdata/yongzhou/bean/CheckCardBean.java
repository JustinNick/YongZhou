package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/12/12.
 */

public class CheckCardBean extends BaseBean {

    /**
     * success : false
     * message : Unexpected character encountered while parsing value: <. Path '', line 0, position 0.
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

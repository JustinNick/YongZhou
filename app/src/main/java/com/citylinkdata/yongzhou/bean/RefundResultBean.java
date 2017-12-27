package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/11/15.
 */

public class RefundResultBean {

    private String reqCode;
    private int respStatus;
    private String respMsg;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public int getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(int respStatus) {
        this.respStatus = respStatus;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}

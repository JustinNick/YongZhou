package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by liqing on 2017/11/9.
 */

public class ReAPDUBean {


    /**
     * apduList : [{"apdu":"00A40000023F00"},{"apdu":"00A40000023F01"},{"apdu":"002000000454435053"},{"apdu":"805000020B0100000001000000000000"}]
     * reqCode : 1005
     * respMsg : 请求成功
     * respStatus : 0
     * sequenceId : 20171117153512000015
     * serial : 20171117033512000023
     */

    private String reqCode;
    private String respMsg;
    private int respStatus;
    private String sequenceId;
    private String serial;
    private List<ApduListBean> apduList;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(int respStatus) {
        this.respStatus = respStatus;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public List<ApduListBean> getApduList() {
        return apduList;
    }

    public void setApduList(List<ApduListBean> apduList) {
        this.apduList = apduList;
    }

    public static class ApduListBean {
        /**
         * apdu : 00A40000023F00
         */

        private String apdu;

        public String getApdu() {
            return apdu;
        }

        public void setApdu(String apdu) {
            this.apdu = apdu;
        }
    }
}

package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by liqing on 2017/11/14.
 */

public class RehargeRecordBean {


    /**
     * recodeList : [{"appSerial":"00002014010900000001","chargeCount":"1","result":"交易失败","serial":"20170927104656000015","status":"0","time":"2017-09-27 10:47:36"},{"appSerial":"00002014010900000001","chargeCount":"1","result":"交易失败","serial":"20170927104325000013","status":"0","time":"2017-09-27 10:44:05"},{"appSerial":"00002014010900000001","chargeCount":"1","result":"交易成功","serial":"20170927103947000012","status":"0","time":"2017-09-27 10:39:49"},{"appSerial":"00002014010900000001","chargeCount":"1","result":"交易成功","serial":"20170927103838000011","status":"0","time":"2017-09-27 10:38:44"},{"appSerial":"00002014010900000001","chargeCount":"1","payOrder":"719009518648112119200","result":"交易成功","serial":"20170808100141000090","status":"0","time":"2017-08-08 10:01:46"},{"appSerial":"21540000006742421957","chargeCount":"1","payOrder":"786814669592081177300","result":"交易失败","serial":"20170808095918000088","status":"1","time":"2017-08-08 09:59:58"},{"appSerial":"00002014010900000001","chargeCount":"1","payOrder":"620677644139361553600","result":"交易成功","serial":"20170808095600000087","status":"0","time":"2017-08-08 09:56:10"},{"appSerial":"00002014010900000001","chargeCount":"1","payOrder":"620668387074814373901","result":"交易失败","serial":"20170807091029000061","status":"1","time":"2017-08-07 09:10:41"},{"appSerial":"00002014010900000001","chargeCount":"1","payOrder":"886935768738440233000","result":"交易失败","serial":"20170804035042000030","status":"3","time":"2017-08-04 15:51:22"},{"appSerial":"00002014010900000001","chargeCount":"1","result":"交易失败","serial":"20170804034808000028","status":"0","time":"2017-08-04 15:48:48"}]
     * reqCode : 1007
     * respMsg : 查询记录成功
     * respStatus : 0
     * totalNum : 70
     */

    private String reqCode;
    private String respMsg;
    private String respStatus;
    private int totalNum;
    private List<RecordeListBean> recodeList;

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

    public String getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(String respStatus) {
        this.respStatus = respStatus;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<RecordeListBean> getRecodeList() {
        return recodeList;
    }

    public void setRecodeList(List<RecordeListBean> recodeList) {
        this.recodeList = recodeList;
    }

    public static class RecordeListBean {
        /**
         * appSerial : 00002014010900000001
         * chargeCount : 1
         * result : 交易失败
         * serial : 20170927104656000015
         * status : 0
         * time : 2017-09-27 10:47:36
         * payOrder : 719009518648112119200
         */

        private String appSerial;
        private double chargeCount;
        private String result;
        private String serial;
        private int status;
        private String time;
        private String payOrder;

        public String getAppSerial() {
            return appSerial;
        }

        public void setAppSerial(String appSerial) {
            this.appSerial = appSerial;
        }

        public double getChargeCount() {
            return chargeCount/100;
        }

        public void setChargeCount(double chargeCount) {
            this.chargeCount = chargeCount;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPayOrder() {
            return payOrder;
        }

        public void setPayOrder(String payOrder) {
            this.payOrder = payOrder;
        }
    }
}

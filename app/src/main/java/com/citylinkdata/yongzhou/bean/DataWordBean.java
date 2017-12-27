package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by h.bob on 2017/11/17.
 */

public class DataWordBean {

    /**
     * success : true
     * message :
     * data : [{"Name":"卡片业务","Value":"","Code":"QSCard","Ident":"QuestionType","SerialNum":1,"ID":"2f23bd2d-0acf-439c-88fa-2ed389c10567","CreatedTime":"2017-11-02T14:59:07","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":""},{"Name":"充值业务","Value":"","Code":"QSRecharge","Ident":"QuestionType","SerialNum":2,"ID":"ad08c754-d0b1-4f06-93e2-36a003c62d3d","CreatedTime":"2017-11-02T15:00:14","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":""},{"Name":"年审服务","Value":"","Code":"QSVerification","Ident":"QuestionType","SerialNum":3,"ID":"cf295e78-c6f3-42d7-a390-f20562b3692a","CreatedTime":"2017-11-02T15:01:07","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":""}]
     */

    private boolean success;
    private String message;
    private List<DataWordDetailBean> data;

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

    public List<DataWordDetailBean> getData() {
        return data;
    }

    public void setData(List<DataWordDetailBean> data) {
        this.data = data;
    }

    public static class DataWordDetailBean {
        /**
         * Name : 卡片业务
         * Value :
         * Code : QSCard
         * Ident : QuestionType
         * SerialNum : 1
         * ID : 2f23bd2d-0acf-439c-88fa-2ed389c10567
         * CreatedTime : 2017-11-02T14:59:07
         * DeletedTime : 2000-01-01T00:00:00
         * DeleteState : 0
         * Remark :
         */

        private String Name;
        private String Value;
        private String Code;
        private String Ident;
        private int SerialNum;
        private String ID;
        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private String Remark;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getIdent() {
            return Ident;
        }

        public void setIdent(String Ident) {
            this.Ident = Ident;
        }

        public int getSerialNum() {
            return SerialNum;
        }

        public void setSerialNum(int SerialNum) {
            this.SerialNum = SerialNum;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCreatedTime() {
            return CreatedTime;
        }

        public void setCreatedTime(String CreatedTime) {
            this.CreatedTime = CreatedTime;
        }

        public String getDeletedTime() {
            return DeletedTime;
        }

        public void setDeletedTime(String DeletedTime) {
            this.DeletedTime = DeletedTime;
        }

        public int getDeleteState() {
            return DeleteState;
        }

        public void setDeleteState(int DeleteState) {
            this.DeleteState = DeleteState;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}

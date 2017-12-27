package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/11/2.
 */

public class ReRegistBean extends BaseBean {


    /**
     * success : true
     * message :
     * data : {"mobileNo":"13622546332","loginPwd":"F293C26511DB037C","NewloginPwd":null,"mobileType":"SM-J3109","AppVersion":"Android","ID":"63089ade-7403-48a0-99c5-8bd6b695c523","CreatedTime":"2017-11-02T14:01:21.3168085+08:00","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null}
     */

    private boolean success;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobileNo : 13622546332
         * loginPwd : F293C26511DB037C
         * NewloginPwd : null
         * mobileType : SM-J3109
         * AppVersion : Android
         * ID : 63089ade-7403-48a0-99c5-8bd6b695c523
         * CreatedTime : 2017-11-02T14:01:21.3168085+08:00
         * DeletedTime : 2000-01-01T00:00:00
         * DeleteState : 0
         * Remark : null
         */

        private String mobileNo;
        private String loginPwd;
        private Object NewloginPwd;
        private String mobileType;
        private String AppVersion;
        private String ID;
        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private Object Remark;

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getLoginPwd() {
            return loginPwd;
        }

        public void setLoginPwd(String loginPwd) {
            this.loginPwd = loginPwd;
        }

        public Object getNewloginPwd() {
            return NewloginPwd;
        }

        public void setNewloginPwd(Object NewloginPwd) {
            this.NewloginPwd = NewloginPwd;
        }

        public String getMobileType() {
            return mobileType;
        }

        public void setMobileType(String mobileType) {
            this.mobileType = mobileType;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String AppVersion) {
            this.AppVersion = AppVersion;
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

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }
    }
}

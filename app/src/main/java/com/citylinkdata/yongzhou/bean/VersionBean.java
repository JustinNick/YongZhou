package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/11/3.
 */

public class VersionBean extends BaseBean {


    /**
     * success : true
     * message :
     * data : {"version_name":"1.0.0","version_code":"0","platform":"Android","upgrade_method":null,"upgrade_url":"http://yzapp.gold-pace.com:82/Apps/v1.0.0.apk","status":null,"description":"初始化版本","ID":"3cf306dc-66e4-4a5b-aec7-f42ffacf38a1","CreatedTime":"2017-11-01T12:22:33","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null}
     */

    private boolean success;
    private String message;
    private Version data;

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

    public Version getData() {
        return data;
    }

    public void setData(Version data) {
        this.data = data;
    }

    public static class Version {
        /**
         * version_name : 1.0.0
         * version_code : 0
         * platform : Android
         * upgrade_method : null
         * upgrade_url : http://yzapp.gold-pace.com:82/Apps/v1.0.0.apk
         * status : null
         * description : 初始化版本
         * ID : 3cf306dc-66e4-4a5b-aec7-f42ffacf38a1
         * CreatedTime : 2017-11-01T12:22:33
         * DeletedTime : 2000-01-01T00:00:00
         * DeleteState : 0
         * Remark : null
         */

        private String version_name;
        private String version_code;
        private String platform;
        private String upgrade_method;
        private String upgrade_url;
        private Object status;
        private String description;
        private String ID;
        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private Object Remark;

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public Object getUpgrade_method() {
            return upgrade_method;
        }

        public void setUpgrade_method(String upgrade_method) {
            this.upgrade_method = upgrade_method;
        }

        public String getUpgrade_url() {
            return upgrade_url;
        }

        public void setUpgrade_url(String upgrade_url) {
            this.upgrade_url = upgrade_url;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

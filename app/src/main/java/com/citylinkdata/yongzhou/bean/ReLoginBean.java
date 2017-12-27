package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/11/2.
 */

public class ReLoginBean extends BaseBean {


    /**
     * success : true
     * message :
     * data : {"mobileNo":"13697735117","loginPwd":"50CEC709780ABB73","NewloginPwd":null,"mobileType":"asd","AppVersion":"AppVersion","ID":"d9bbc823-b75a-4f1d-b64b-2fbe84618eb9","CreatedTime":"2017-10-31T13:51:56","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null}
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
         * mobileNo : 13697735117
         * loginPwd : 50CEC709780ABB73
         * NewloginPwd : null
         * mobileType : asd
         * AppVersion : AppVersion
         * ID : d9bbc823-b75a-4f1d-b64b-2fbe84618eb9
         * CreatedTime : 2017-10-31T13:51:56
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
        private String Token;

        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private Object Remark;

        private String Nickname;
        private String Realname;
        private String IDCode;
        private String Gender;
        private String AvatarPictureUrl;

        public String getAvatarPictureUrl() {
            return AvatarPictureUrl;
        }

        public void setAvatarPictureUrl(String avatarPictureUrl) {
            AvatarPictureUrl = avatarPictureUrl;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String nickname) {
            Nickname = nickname;
        }

        public String getRealname() {
            return Realname;
        }

        public void setRealname(String realname) {
            Realname = realname;
        }

        public String getIDCode() {
            return IDCode;
        }

        public void setIDCode(String IDCode) {
            this.IDCode = IDCode;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

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

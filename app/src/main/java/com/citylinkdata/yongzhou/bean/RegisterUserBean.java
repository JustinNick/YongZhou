package com.citylinkdata.yongzhou.bean;

/**
 * Created by dell on 2017/11/21.
 */

public class RegisterUserBean {

    /**
     * success : true
     * message : 修改成功。
     * data : {"Nickname":"（︶︿︶）=凸","AvatarPictureUrl":"","mobileNo":"13622546332","loginPwd":"5210C3BC455ED8AB7A256D08ED4D2067","NewloginPwd":"","mobileType":"SM-J3109","AppVersion":"Android","Token":"","MoneyBalance":857.83,"IntegralBalance":0,"Realname":"木子李","IDCode":"452622198810111245","Gender":"男","ID":"0dbb1466-c3b5-4179-8af5-697cdff9d1de","CreatedTime":"2017-11-16T13:51:13","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":""}
     */

    private boolean success;
    private String message;
    private UpdateUserBean data;

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

    public UpdateUserBean getData() {
        return data;
    }

    public void setData(UpdateUserBean data) {
        this.data = data;
    }

    public static class UpdateUserBean {
        /**
         * Nickname : （︶︿︶）=凸
         * AvatarPictureUrl :
         * mobileNo : 13622546332
         * loginPwd : 5210C3BC455ED8AB7A256D08ED4D2067
         * NewloginPwd :
         * mobileType : SM-J3109
         * AppVersion : Android
         * Token :
         * MoneyBalance : 857.83
         * IntegralBalance : 0
         * Realname : 木子李
         * IDCode : 452622198810111245
         * Gender : 男
         * ID : 0dbb1466-c3b5-4179-8af5-697cdff9d1de
         * CreatedTime : 2017-11-16T13:51:13
         * DeletedTime : 2000-01-01T00:00:00
         * DeleteState : 0
         * Remark :
         */

        private String Nickname;
        private String AvatarPictureUrl;
        private String mobileNo;
        private String loginPwd;
        private String NewloginPwd;
        private String mobileType;
        private String AppVersion;
        private String Token;
        private double MoneyBalance;
        private int IntegralBalance;
        private String Realname;
        private String IDCode;
        private String Gender;
        private String ID;
        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private String Remark;

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String Nickname) {
            this.Nickname = Nickname;
        }

        public String getAvatarPictureUrl() {
            return AvatarPictureUrl;
        }

        public void setAvatarPictureUrl(String AvatarPictureUrl) {
            this.AvatarPictureUrl = AvatarPictureUrl;
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

        public String getNewloginPwd() {
            return NewloginPwd;
        }

        public void setNewloginPwd(String NewloginPwd) {
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

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public double getMoneyBalance() {
            return MoneyBalance;
        }

        public void setMoneyBalance(double MoneyBalance) {
            this.MoneyBalance = MoneyBalance;
        }

        public int getIntegralBalance() {
            return IntegralBalance;
        }

        public void setIntegralBalance(int IntegralBalance) {
            this.IntegralBalance = IntegralBalance;
        }

        public String getRealname() {
            return Realname;
        }

        public void setRealname(String Realname) {
            this.Realname = Realname;
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

        public void setGender(String Gender) {
            this.Gender = Gender;
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

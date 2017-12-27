package com.citylinkdata.yongzhou.bean;

/**
 * Created by dell on 2017/11/13.
 */

public class QueryBalanceBean {

    /**
     * success : true
     * message : 查询成功。
     * data : {"Username":"13622546332","Balance":"89013","FrozenBalance":"0"}
     */

    private boolean success;
    private String message;
    private BalanceInfoBean data;

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

    public BalanceInfoBean getData() {
        return data;
    }

    public void setData(BalanceInfoBean data) {
        this.data = data;
    }

    public static class BalanceInfoBean {
        /**
         * Username : 13622546332
         * Balance : 89013
         * FrozenBalance : 0
         */

        private String Username;
        private int Balance;
        private int FrozenBalance;

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }

        public int getBalance() {
            return Balance;
        }

        public void setBalance(int Balance) {
            this.Balance = Balance;
        }

        public int getFrozenBalance() {
            return FrozenBalance;
        }

        public void setFrozenBalance(int FrozenBalance) {
            this.FrozenBalance = FrozenBalance;
        }
    }
}

package com.citylinkdata.yongzhou.bean;

/**
 * Created by liqing on 2017/11/30.
 */

public class UserAvatorBean extends BaseBean {


    /**
     * success : true
     * message : 上传成功。
     * data : {"PictureUrl":"http://yzapp.gold-pace.com:82/UploadFiles//Avators/2017-11-30/20171130111740-314.png"}
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
         * PictureUrl : http://yzapp.gold-pace.com:82/UploadFiles//Avators/2017-11-30/20171130111740-314.png
         */

        private String PictureUrl;

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
        }
    }
}

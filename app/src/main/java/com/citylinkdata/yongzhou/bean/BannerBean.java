package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by dell on 2017/11/14.
 */

public class BannerBean {

    /**
     * success : true
     * message :
     * data : [{"ID":"f7efed03-759b-4d74-ab00-56b940143f3d","Title":"测试轮播图2","CreatedTime":"2017-11-14T18:35:15","PictureUrl":"http://yzapp.gold-pace.com:82/UploadFiles/2017-11-14/20171114183520-007.png","EventType":"url","EventExcutePath":"http://www.qq.com"}]
     */

    private boolean success;
    private String message;
    private List<BannerDetailBean> data;

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

    public List<BannerDetailBean> getData() {
        return data;
    }

    public void setData(List<BannerDetailBean> data) {
        this.data = data;
    }

    public static class BannerDetailBean {
        /**
         * ID : f7efed03-759b-4d74-ab00-56b940143f3d
         * Title : 测试轮播图2
         * CreatedTime : 2017-11-14T18:35:15
         * PictureUrl : http://yzapp.gold-pace.com:82/UploadFiles/2017-11-14/20171114183520-007.png
         * EventType : url
         * EventExcutePath : http://www.qq.com
         */

        private String ID;
        private String Title;
        private String CreatedTime;
        private String PictureUrl;
        private String EventType;
        private String EventExcutePath;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getCreatedTime() {
            return CreatedTime;
        }

        public void setCreatedTime(String CreatedTime) {
            this.CreatedTime = CreatedTime;
        }

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
        }

        public String getEventType() {
            return EventType;
        }

        public void setEventType(String EventType) {
            this.EventType = EventType;
        }

        public String getEventExcutePath() {
            return EventExcutePath;
        }

        public void setEventExcutePath(String EventExcutePath) {
            this.EventExcutePath = EventExcutePath;
        }
    }
}

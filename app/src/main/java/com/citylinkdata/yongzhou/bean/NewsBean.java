package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by liqing on 2017/11/3.
 */

public class NewsBean extends BaseBean {


    /**
     * success : true
     * message :
     * data : [{"ID":"8aca40b5-d53f-4323-8449-311810be9df1","Title":"新闻标题2","CreatedTime":"2017-11-02T15:51:32","Author":"发布人"},{"ID":"4b1672be-55b5-4cb2-bf55-bfa2d0a7fb40","Title":"新闻标题","CreatedTime":"2017-11-02T15:49:58","Author":"发布人"}]
     */

    private boolean success;
    private String message;
    private List<News> data;

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

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    public static class News {
        /**
         * ID : 8aca40b5-d53f-4323-8449-311810be9df1
         * Title : 新闻标题2
         * CreatedTime : 2017-11-02T15:51:32
         * Author : 发布人
         * Picture : http://www.domain.com/folder/filename.png
         */

        private String ID;
        private String Title;
        private String CreatedTime;
        private String Author;
        private String Picture;

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

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }
    }
}

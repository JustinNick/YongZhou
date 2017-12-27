package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by liqing on 2017/11/30.
 */

public class OutletsBean extends BaseBean {


    /**
     * success : true
     * message :
     * data : [{"Name":"冷水滩中心营业厅","Adress":"永州市冷水滩区逸云路46号","Time":"夏季8:00-18:00 冬季8:30-17:30","Tel":"0746-8223456","lat":"26.426546","lng":"111.627501","Area":"63a03e4f-d1f6-4210-a5e8-563791a7a205","Type":"5ba1233b-4050-4c92-9f24-05a44a1365dc","BTypes":"充值,售卡","AreaName":"冷水滩区","TypeName":"自营","ID":"a475e426-e65b-4863-8d0c-63990c18f62a","CreatedTime":"2017-11-29T13:57:56","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null},{"Name":"通程营业厅","Adress":"冷水滩区零陵中路629号永泰商贸城","Time":"夏季9:00-18:00 冬季9:30-17:30","Tel":"0746-8223456","lat":" 26.455156","lng":"111.607581","Area":"00000000-0000-0000-0000-000000000000","Type":"00000000-0000-0000-0000-000000000000","BTypes":"充值","AreaName":null,"TypeName":null,"ID":"d7affacf-be90-4e2c-9e1f-aea8451ab05d","CreatedTime":"2017-11-29T14:12:35","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null},{"Name":"零陵中心营业厅","Adress":"零陵区南津中路393号","Time":"夏季8:00-18:00 冬季8:30-17:30","Tel":"0746-6388677","lat":" 26.236101","lng":"111.636157","Area":"00000000-0000-0000-0000-000000000000","Type":"00000000-0000-0000-0000-000000000000","BTypes":"售卡","AreaName":null,"TypeName":null,"ID":"cec506b4-8f1f-4f53-8f96-b93cb9699361","CreatedTime":"2017-11-29T14:17:17","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null},{"Name":"徐家井三联电器营业厅","Adress":"零陵区芝山路73号","Time":"夏季9:00-18:00 冬季9:30-17:30","Tel":"0746-8223456","lat":" 26.233613","lng":"111.628312","Area":"a475e426-e65b-4863-8d0c-63990c18f62a","Type":"a475e426-e65b-4863-8d0c-63990c18f62a","BTypes":null,"AreaName":"","TypeName":"","ID":"f7c8c7b3-7cbb-48f3-b67e-d2b3a55e3697","CreatedTime":"2017-11-29T14:17:56","DeletedTime":"2000-01-01T00:00:00","DeleteState":0,"Remark":null}]
     */

    private boolean success;
    private String message;
    private List<Outlets> data;

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

    public List<Outlets> getData() {
        return data;
    }

    public void setData(List<Outlets> data) {
        this.data = data;
    }

    public static class Outlets {
        /**
         * Name : 冷水滩中心营业厅
         * Adress : 永州市冷水滩区逸云路46号
         * Time : 夏季8:00-18:00 冬季8:30-17:30
         * Tel : 0746-8223456
         * lat : 26.426546
         * lng : 111.627501
         * Area : 63a03e4f-d1f6-4210-a5e8-563791a7a205
         * Type : 5ba1233b-4050-4c92-9f24-05a44a1365dc
         * BTypes : 充值,售卡
         * AreaName : 冷水滩区
         * TypeName : 自营
         * ID : a475e426-e65b-4863-8d0c-63990c18f62a
         * CreatedTime : 2017-11-29T13:57:56
         * DeletedTime : 2000-01-01T00:00:00
         * DeleteState : 0
         * Remark : null
         */

        private String Name;
        private String Adress;
        private String Time;
        private String Tel;
        private double lat;
        private double lng;
        private String Area;
        private String Type;
        private String BTypes;
        private String AreaName;
        private String TypeName;
        private String ID;
        private String CreatedTime;
        private String DeletedTime;
        private int DeleteState;
        private Object Remark;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getAdress() {
            return Adress;
        }

        public void setAdress(String Adress) {
            this.Adress = Adress;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getBTypes() {
            return BTypes;
        }

        public void setBTypes(String BTypes) {
            this.BTypes = BTypes;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
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

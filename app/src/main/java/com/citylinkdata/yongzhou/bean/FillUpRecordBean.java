package com.citylinkdata.yongzhou.bean;

import java.util.List;

/**
 * Created by liqing on 2017/12/12.
 */

public class FillUpRecordBean {


    /**
     * boardRecordList : [{"action_no":"","card_money":0,"card_no":"4250300188000123","city_code":"746","consume_code":"595671","money":5000,"msisdn":"13622546332","oper_name":"","order_code":"20171212141419737465","order_id":5895,"order_time":"2017-12-12 14:14:19","status":1},{"action_no":"","card_money":0,"card_no":"4250300188000123","city_code":"746","consume_code":"595668","money":3000,"msisdn":"13622546332","oper_name":"","order_code":"20171212135138374357","order_id":5893,"order_time":"2017-12-12 13:51:38","status":1},{"action_no":"","card_money":0,"card_no":"4250586325147850","city_code":"746","consume_code":"595666","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171212133950234377","order_id":5891,"order_time":"2017-12-12 13:39:50","status":1},{"action_no":"","card_money":0,"card_no":"5213421523124125","city_code":"746","consume_code":"595656","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171212095958991888","order_id":5889,"order_time":"2017-12-12 09:59:58","status":1},{"action_no":"","card_money":0,"card_no":"4250340100020030","city_code":"746","consume_code":"595555","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171211141801892507","order_id":5879,"order_time":"2017-12-11 14:18:01","status":1},{"action_no":"","card_money":0,"card_no":"4250340100020030","city_code":"746","consume_code":"595550","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171211135423314013","order_id":5877,"order_time":"2017-12-11 13:54:23","status":1},{"action_no":"","card_money":0,"card_no":"4250340100020030","city_code":"746","consume_code":"595540","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171211104637475404","order_id":5875,"order_time":"2017-12-11 10:46:37","status":1},{"action_no":"","card_money":0,"card_no":"4250340100020030","city_code":"746","consume_code":"595537","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171211103558731812","order_id":5873,"order_time":"2017-12-11 10:35:58","status":1},{"action_no":"","card_money":0,"card_no":"4250340100020030","city_code":"746","consume_code":"595534","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171211103104243870","order_id":5871,"order_time":"2017-12-11 10:31:04","status":1},{"action_no":"","card_money":0,"card_no":"1234567891234567","city_code":"746","consume_code":"594456","money":1,"msisdn":"13622546332","oper_name":"","order_code":"20171206171857555760","order_id":5357,"order_time":"2017-12-06 17:18:57","status":1}]
     * reqCode : 1045
     * respMsg : 查询补登记录成功
     * respStatus : 0
     * totalNum : 12
     */

    private String reqCode;
    private String respMsg;
    private String respStatus;
    private int totalNum;
    private List<BoardRecordListBean> boardRecordList;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(String respStatus) {
        this.respStatus = respStatus;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<BoardRecordListBean> getBoardRecordList() {
        return boardRecordList;
    }

    public void setBoardRecordList(List<BoardRecordListBean> boardRecordList) {
        this.boardRecordList = boardRecordList;
    }

    public static class BoardRecordListBean {
        /**
         * action_no :
         * card_money : 0
         * card_no : 4250300188000123
         * city_code : 746
         * consume_code : 595671
         * money : 5000
         * msisdn : 13622546332
         * oper_name :
         * order_code : 20171212141419737465
         * order_id : 5895
         * order_time : 2017-12-12 14:14:19
         * status : 1
         */

        private String action_no;
        private double card_money;
        private String card_no;
        private String city_code;
        private String consume_code;
        private double money;
        private String msisdn;
        private String oper_name;
        private String order_code;
        private int order_id;
        private String order_time;
        private int status;

        public String getAction_no() {
            return action_no;
        }

        public void setAction_no(String action_no) {
            this.action_no = action_no;
        }

        public double getCard_money() {
            return card_money;
        }

        public void setCard_money(int card_money) {
            this.card_money = card_money;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getConsume_code() {
            return consume_code;
        }

        public void setConsume_code(String consume_code) {
            this.consume_code = consume_code;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getOper_name() {
            return oper_name;
        }

        public void setOper_name(String oper_name) {
            this.oper_name = oper_name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

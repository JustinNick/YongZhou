package com.citylinkdata.yongzhou.bean;

import com.citylinkdata.yongzhou.BaseApplication;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.utils.SystemUtil;

/**
 * Created by liqing on 2017/11/9.
 */

public class APDUBean extends BaseBean{

    /**
     * 接口报文唯一标识 -
     * 1005 充值
     * 1006 补登
     * 1010 可疑验证
     * 1011 卡片信息认证
     * 1012 修改有效期
     */
    private  String reqCode;
    /**
     * 会话ID
     */
    private  String  sequenceId;
    /**
     * 用户名
     */
    private  String  loginUser ;
    /**
     * 电话号码
     */
    private  String  mobileNo;;
    /**
     * Ic 卡卡号
     */
    private  String  cardNo;
    /**
     * 充值金额
     */
    private  String  money;
    /**
     * 应用唯一标识
     */
    private  String  appId ;
    /**
     * 交易密码
     */
    private  String  chargePwd;
    /**
     * 充值渠道
     */
    private  String  payChannel;
    /**
     * 流水号，reqCode为1005时需要传
     */
    private  String  tn;
    /**
     * 卡上余额
     */
    private  String  cardBalance;
    /**
     * 最后一条记录
     */
    private  String  lastRecord;
    /**
     * 手机MEID
     */
    private  String  meid;
    /**
     * 应用AID
     */
    private  String  currentAppAid;
    /**
     * 手机型号
     */
    private  String  mobileType;
    /**
     * 已执行成功的APDU条数
     */
    private  String  apduSum;
    /**
     * 最后执行的一条APDU执行结果SW
     */
    private  String  lastApduSW;
    /**
     * 最后执行的一条指令的执行返回数据
     */
    private  String  lastData;
    /**
     * 最后执行的一条APDU命令
     */
    private  String  lastApdu;
    /**
     * 报文签名
     */
    private  String  sigNature;
    /**
     * 会话ID
     */
    private  String  token;

    public APDUBean(String reqCode, String sequenceId, String cardNo, String money,  String chargePwd, String payChannel, String tn,
                    String cardBalance, String lastRecord, String apduSum, String lastApduSW, String lastData,
                    String lastApdu, String sigNature) {
        this.reqCode = reqCode;
        this.sequenceId = sequenceId;
        this.loginUser =  SPUtils.getCache(BaseApplication.getInstance(), SPString.USERNAME);;
        this.mobileNo =   SPUtils.getCache(BaseApplication.getInstance(), SPString.USERNAME);
        this.cardNo = cardNo;
        this.money = money;
        this.appId = SPString.APPID;
        this.chargePwd = chargePwd;
        this.payChannel = payChannel;
        this.tn = tn;
        this.cardBalance = cardBalance;
        this.lastRecord = lastRecord;
        this.meid = SystemUtil.getIMEI(BaseApplication.getInstance());
        this.currentAppAid = SPString.CURRENTAPPAID;
        this.mobileType = SystemUtil.getSystemModel();
        this.apduSum = apduSum;
        this.lastApduSW = lastApduSW;
        this.lastData = lastData;
        this.lastApdu = lastApdu;
        this.sigNature = sigNature;
        this.token = "";
    }


    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChargePwd() {
        return chargePwd;
    }

    public void setChargePwd(String chargePwd) {
        this.chargePwd = chargePwd;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(String lastRecord) {
        this.lastRecord = lastRecord;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getCurrentAppAid() {
        return currentAppAid;
    }

    public void setCurrentAppAid(String currentAppAid) {
        this.currentAppAid = currentAppAid;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getApduSum() {
        return apduSum;
    }

    public void setApduSum(String apduSum) {
        this.apduSum = apduSum;
    }

    public String getLastApduSW() {
        return lastApduSW;
    }

    public void setLastApduSW(String lastApduSW) {
        this.lastApduSW = lastApduSW;
    }

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public String getLastApdu() {
        return lastApdu;
    }

    public void setLastApdu(String lastApdu) {
        this.lastApdu = lastApdu;
    }

    public String getSigNature() {
        return sigNature;
    }

    public void setSigNature(String sigNature) {
        this.sigNature = sigNature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

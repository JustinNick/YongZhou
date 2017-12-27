package com.citylink.tsm.citycard.cityic;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.citylink.tsm.citycard.CityCompatible;
import com.citylink.tsm.citycard.bean.CardPOR;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylink.tsm.citycard.utils.ByteUtils;
import com.citylink.tsm.citycard.utils.FormatUtils;
import com.citylink.tsm.citycard.utils.ZLogCard;
import com.citylinkdata.cardnfc.CityStruct;
import com.citylinkdata.cardnfc.NFCCardManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通用城市
 * Created by zgm on 2016/12/7.
 */
public class UnionIC extends CityCompatible {

    private List<String> mXmlCmdKey = null;
    private boolean  mSelectFile  = true;

    private String mAppSerial   = null;
    private String mCardNum   = null;
    private String mAppCityCode = null;
    private String mCardCityCode = null;
    private String mTerminalNo= null;
    private String mBlance =null;

    private  ArrayList<TradeRecord> mLocalRecode = null;
    private  ArrayList<TradeRecord> mOtherRecode = null;
    private  ArrayList<TradeRecord> mChargeRecode = null;

    public UnionIC(){
        mXmlCmdKey = Collections.synchronizedList(new ArrayList<String>());
    }

    /**
     * 选择文件
     * @return
     */
    private boolean initial() {
        mXmlCmdKey.add(0, "city_file_cmd");
        NFCCardManager.excuXmlCmd();
        return mSelectFile;
    }


    /**
     * 卡架构回调方法
     * 指令应答解析
     */
    @Override
    public boolean excutCmdResponse(String arg0, String arg1, byte[] arg2) {
        // TODO Auto-generated method stub
        ZLogCard.d("excutCmdResponse exect_cmd =" + arg0 + " - " + arg1 + " - " + ByteUtils.getHex(arg2));

        if ("city_file_cmd".equals(arg0)) { /**选择文件*/
            String resp = ByteUtils.getHex(arg2);
            if ("00A40000021001".equals(arg1)) {
                return selectFileResponse(resp);
            } else if ("00A40000023F01".equals(arg1)) {
                return selectFileResponse(resp);
            } else if (!"00A40000023F00".equals(arg1)) {
                if (resp != null && resp.endsWith("9000")) {
                    mSelectFile = true;
                    return false;
                }
            }
        } else if ("local_recoder".equals(arg0)) {
            String resp = ByteUtils.getHex(arg2);
            mLocalRecode = readRecord(resp, null);
        } else if ("remove_recoder".equals(arg0)) {
            String resp = ByteUtils.getHex(arg2);
            mOtherRecode = readRecord(resp, null);
        } else if ("charge_recode".equals(arg0)) {
            String resp = ByteUtils.getHex(arg2);
            mChargeRecode = readRecord(resp, "02");
        } else if ("terminalno".equals(arg0)) {
            String resp = ByteUtils.getHex(arg2);
            mTerminalNo = getTerminalNo(resp);
        } else if ("appserial".equals(arg0)) {  /**卡号*/
            if ("00B0950C08".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mAppSerial = ByteUtils.getHex(arg2);
            } else if ("00B0950202".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mAppCityCode = ByteUtils.getHex(arg2);
                getFormatAppSerial(mAppSerial, mAppCityCode);
            } else if ("00B095000A0A".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mAppSerial = ByteUtils.getHex(arg2);
            }

        } else if ("cardnum".equals(arg0)) {  /**卡号*/
            if ("00B0950C08".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mCardNum = ByteUtils.getHex(arg2);
            } else if ("00B0950202".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mCardCityCode = ByteUtils.getHex(arg2);
                getFormatAppCardNum(mCardNum, mCardCityCode);
            } else if ("00B095000A0A".equals(arg1) &&
                    jugementResponse(ByteUtils.getHex(arg2))) {
                mCardNum = ByteUtils.getHex(arg2);
            }
        } else if ("blance".equals(arg0)) {   /**余额*/
            String resp = ByteUtils.getHex(arg2);
            mBlance = getBalance(resp);
        }

        return true;
    }

    private boolean selectFileResponse(String resp){
        if (resp != null && resp.endsWith("9000")) {
            mSelectFile = true;
            return false;
        } else {
            mSelectFile = false;
            return true;
        }
    }

    private boolean jugementResponse(String resp){
        if (resp != null && resp.endsWith("9000")) {
            return true;
        } else {
            return false;
        }
    }



    private String getFormatAppSerial(String resp, String code){
        if ((resp.endsWith("9000")) && (code.endsWith("9000"))) {
            mAppSerial = code.substring(0, code.length() - 4)
                    + resp.substring(0, resp.length() - 4);
        }else{
            mAppSerial = null;
        }
        return mAppSerial;
    }

    
    private String getFormatAppCardNum(String resp, String code){
        if ((resp.endsWith("9000")) && (code.endsWith("9000"))) {
            mCardNum = code.substring(0, code.length() - 4)
                    + resp.substring(0, resp.length() - 4);
        }else{
            mCardNum = null;
        }
        return mCardNum;
    }


    /**
     * 卡架构回调方法
     * 指定执行配置文件指令；
     */
    @Override
    public List<String> excutXMLSelectedCmd() {
        // TODO Auto-generated method stub
        return mXmlCmdKey;
    }

    /**
     * 卡架构回调方法
     * 执行配置文件指令后执行该方法；
     */
    @Override
    public void responseBackgroud() {
    }

    /**
     * 卡上信息数据
     */
    @Override
    public Message getCardInfo(String[] arg0) {
        synchronized (UnionIC.class) {
            // TODO Auto-generated method stub
            Message msg = Message.obtain();
            Bundle budle = new Bundle();
            if (arg0 != null && arg0.length > 0 && initial()) {
                for (int i = 0; i < arg0.length; i++) {
                    mXmlCmdKey.clear();
                    switch (arg0[i]) {
                        case CARD_SERIAL:
                            mXmlCmdKey.add("appserial");
                            /**开始执行配置文件指令*/
                            NFCCardManager.excuXmlCmd();
                            budle.putString(CARD_SERIAL, mAppSerial);
                            break;
                        case CARD_NUM:
                            mXmlCmdKey.add("appserial");
                            /**开始执行配置文件指令*/
                            NFCCardManager.excuXmlCmd();
                            budle.putString(CARD_NUM, mAppSerial);
                            break;
                        case CARD_BLANCE:
                            mXmlCmdKey.add("blance");
                            /**开始执行配置文件指令*/
                            NFCCardManager.excuXmlCmd();
                            budle.putString(CARD_BLANCE,mBlance);
                            break;
                        case CARD_TERMINALNO:
                            mXmlCmdKey.add("terminalno");
                            /**开始执行配置文件指令*/
                            NFCCardManager.excuXmlCmd();
                            budle.putString(CARD_TERMINALNO, mTerminalNo);
                            break;
                        case CARD_LAST_RECODE:  /**最后一条记录*/
                            budle.putString(CARD_LAST_RECODE, readLastRecord());
                            break;
                        case CARD_LOCAL_RECODE:
                            budle.putParcelableArrayList(CARD_LOCAL_RECODE, getmLocalRecode());
                            break;
                        case CARD_REMOTE_RECODE:
                            budle.putParcelableArrayList(CARD_REMOTE_RECODE, getmOtherRecode());
                            break;
                        case CARD_CHARGE_RECODE:
                            budle.putParcelableArrayList(CARD_CHARGE_RECODE, getmChargeRecode());
                            break;
                    }
                }
            }
            msg.setData(budle);
            return msg;
        }

    }

    /**
     * 二次格式解析后的Serial
     * @return
     */
    public String  getCardSerial(String cardSerial){

        return  cardSerial;
    }

    /**
     * 二次格式解析后的cardNum
     * @return
     */
    public String getCardNum(String cardNum){
     
        return  cardNum;
    }

    /**
     * 二次格式解析后余额
     * @return
     */
    public String getCardBlance(String balance){
        return  FormatUtils.formatAmount(balance);
    }

    /**
     * 执行APDUList
     *
     * @param
     * @return
     */
    public CardPOR transceiveApduList(ArrayList<String> apduList, String aid) {

        ZLogCard.i("executeApdus");
        String resp = "8800";
        CardPOR cardPOR = new CardPOR();
        int succedNum = 0;
        for (String apdu : apduList) {
            byte[] temResp = NFCCardManager.execuCmd(apdu);
            resp = ByteUtils.getHex(temResp);
            cardPOR.setLastApdu(apdu);
            cardPOR.setLastData(resp);
            cardPOR.setLastApduSW(getSW(resp));
            if (!"9000".equals(getSW(resp)))
                break;
            cardPOR.setAPDUSum("" + (++succedNum));
        }
        return cardPOR;
    }


    /**
     * 执行深圳通APDU指令
     * flag 0：下载和删除： 2：充值 加flag区分充值或者下载不同流程传递的参数
     * */
    public CardPOR transceiveApduList(ArrayList<String> apduList, int flag,String aid) {

        return transceiveApduList(apduList,aid);
    }
	

    /**
     * 读取卡内最后一条记录；
     * @return
     */
    private String readLastRecord(){
        String resp = "8800";
        String file = null;
        CityStruct cityStruct = NFCCardManager.getCityStruct();
        if(cityStruct != null &&cityStruct.mXmlCityCode != null &&
                cityStruct.mXmlCityCode.equals("5899")){ //海口
            file = "00A40000020018";
        }else{
            file = "00A4000002001A";
        }
        byte[] temResp = NFCCardManager.execuCmd(file);
        String selectFileResult = ByteUtils.getHex(temResp);
        //String selectFileResult = transceive(file);
        if (selectFileResult != null && selectFileResult.endsWith("9000")) {// 选择文件成功
            String apduStr = null;
            apduStr = "00B2010417";
            byte[] lastResp = NFCCardManager.execuCmd(apduStr);
            resp = ByteUtils.getHex(lastResp);
            if(resp != null && resp.equals("6A83")){
                return "00000000000000000000000000000000000000000000009000";
            }
        }
        return resp;
    }


    /**
     * 读取卡上记录
     */
    private void readRecode(String group) {
        mXmlCmdKey.clear();
        mXmlCmdKey.add(group);
        /**开始执行配置文件指令*/
        NFCCardManager.excuXmlCmd();
    }

    /**
     * 读取交易记录
     * (本地、异地、充值)
     */
    private ArrayList<TradeRecord> readRecord(String fileResult,String tradeType) {
        final ArrayList<TradeRecord> list = new ArrayList<TradeRecord>();
        if (fileResult != null && fileResult.endsWith("9000")) {// 选择文件成功
            String apduStr = null;
            for (int i = 1; i < 11; i++) {// 顺序读取01-0A号记录
                apduStr = "00B2" + ByteUtils.getHex(new Byte("" + i))
                        + "0417";
                byte[] temResp =NFCCardManager.execuCmd(apduStr);
                String resp = ByteUtils.getHex(temResp);
                if (TextUtils.isEmpty(resp) || !resp.endsWith("9000"))
                    break;
                if (resp.equals("0000009000"))
                    break;
                if (resp.equals("00000000000000000000000000000000000000000000009000"))
                    break;
                TradeRecord tr = new TradeRecord(resp);
                if (tradeType != null) {
                    if (null != tr && null != tr.tradeType
                            && tr.tradeType.equalsIgnoreCase(tradeType)) {
                        list.add(tr);
                    }
                } else {
                    if (null != tr && null != tr.tradeType
                            && !tr.tradeType.equalsIgnoreCase("02")) {
                        list.add(tr);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 余额解析
     * @param resp
     * @return
     */
    private String getBalance(String resp) {
        String balance = "";
        if (resp.endsWith("9000"))
            balance = resp.substring(0, resp.length() - 4);// 保证新卡金额为0// 而不是默认的80000000
        return balance;
    }

    /**
     * 获取指令应答解析后余额
     * @return
     */
    public String getCardBalance(String balance){
        return FormatUtils.formatAmount(balance);
    }

    /*
    * 获取指令应答后解析为分的余额
    * */
    public String getCardBalanceFen(String balance){
        long amountValue = Long.parseLong(balance, 16);
        return  String.valueOf(amountValue);
    }

    /**
     * terminalNo解析
     * @param resp
     * @return
     */
    private String getTerminalNo(String resp) {
        ZLogCard.i("getTerminalNo");
        String terminalNo = "";
        if (resp.endsWith("9000"))
            terminalNo = resp.substring(0, resp.length() - 4);

        return terminalNo;
    }

    /**
     * serail解析
     * @param appSerial
     * @return
     */
    private String getAppSerial(String appSerial) {// 改为跟电信深圳通一样的getAppSerial// -16-11-3
        String resp = "";
        if (null != appSerial) {
            if (appSerial.endsWith("9000")) {// 51800000D117CE27
                String sn = appSerial.substring(8, appSerial.length() - 4);// D117CE27
                resp = String.valueOf(ByteUtils.LittleEndian(sn));// 小端转换
                // 深圳通后台传递的卡号改为10进制
                // 16-8-28
            }
        }
        return resp;
    }


    private ArrayList<TradeRecord> getmLocalRecode() {
        readRecode("local_recoder");
        return mLocalRecode;
    }


    private ArrayList<TradeRecord> getmOtherRecode() {
        readRecode("remove_recoder");
        return mOtherRecode;
    }

    private ArrayList<TradeRecord> getmChargeRecode() {
        readRecode("charge_recode");
        return mChargeRecode;
    }

}

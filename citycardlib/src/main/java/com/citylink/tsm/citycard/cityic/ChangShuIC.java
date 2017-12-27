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
import com.citylinkdata.cardnfc.NFCCardManager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChangShuIC extends CityCompatible {


	private List<String> mXmlCmdKey = null;
	private boolean mSelectFile = true;

	private String mAppSerial = null;
	private String mAppCityCode = null;
	
	private String mCardNum   = null;
	private String mCardCityCode = null;
	
	private String mTerminalNo = null;
	
	private String mBlance = null;

	private ArrayList<TradeRecord> mLocalRecode = null;
	private ArrayList<TradeRecord> mOtherRecode = null;
	private ArrayList<TradeRecord> mChargeRecode = null;

	public ChangShuIC() {
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
	 * 架构回调
	 * */
	@Override
	public boolean excutCmdResponse(String arg0, String arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		ZLogCard.d("excutCmdResponse exect_cmd =" + arg0 + " - " + arg1 + " - "
				+ ByteUtils.getHex(arg2));
		if ("city_file_cmd".equals(arg0)) {
			/** 选择文件 */
			String resp = ByteUtils.getHex(arg2);
			if ("00A40000021001".equals(arg1)) {
				if (resp != null && resp.endsWith("9000")) {
					mSelectFile = true;
					return false;
				} else {
					mSelectFile = false;
				}
			} else if ("00A40000023F01".equals(arg1)) {
				if (resp != null && resp.endsWith("9000")) {
					mSelectFile = true;
					return false;
				} else {
					mSelectFile = false;
				}
			}
		} else if ("local_recoder".equals(arg0)) {
			String resp = ByteUtils.getHex(arg2);
			mLocalRecode = readRecord(resp,"06");
		} else if ("remove_recoder".equals(arg0)) {
			String resp = ByteUtils.getHex(arg2);
			mOtherRecode = readRecord(resp,"09");
		} else if ("charge_recode".equals(arg0)) {
			String resp = ByteUtils.getHex(arg2);
			mChargeRecode = readRecord(resp,"02");
		} else if ("terminalno".equals(arg0)) {
			String resp = ByteUtils.getHex(arg2);
			mTerminalNo = getTerminalNo(resp);
		} else if ("appserial".equals(arg0)) {
			if ("00B0950C08".equals(arg1)) {
				mAppSerial = ByteUtils.getHex(arg2);
			} else if ("00B0950202".equals(arg1)) {
				mAppCityCode = ByteUtils.getHex(arg2);
				getFormatAppSerial(mAppSerial, mAppCityCode);
			}
		} else if ("cardnum".equals(arg0)) {
			if ("00B0950C08".equals(arg1)) {
				mCardNum = ByteUtils.getHex(arg2);
			} else if ("00B0950202".equals(arg1)) {
				mCardCityCode = ByteUtils.getHex(arg2);
				getFormatCardNum(mCardNum, mCardCityCode);
			}
		} else if ("blance".equals(arg0)) {
			String resp = ByteUtils.getHex(arg2);
			mBlance = getBalance(resp);
		}

		return true;

	}

	@Override
	public List<String> excutXMLSelectedCmd() {
		// TODO Auto-generated method stub
		return mXmlCmdKey;
	}

	@Override
	public Message getCardInfo(String[] arg0) {
		synchronized (ChangShuIC.class) {
			// TODO Auto-generated method stub
			Message msg = Message.obtain();
			Bundle budle = new Bundle();
			if (arg0 != null && arg0.length > 0 && initial()) {
				for (int i = 0; i < arg0.length; i++) {
					mXmlCmdKey.clear();
					ZLogCard.d("arg0[i]-->"+arg0[i]);
					switch (arg0[i]) {
					case CARD_SERIAL:
						mXmlCmdKey.add("appserial");
						/** 开始执行配置文件指令 */
						NFCCardManager.excuXmlCmd();
						budle.putString(CARD_SERIAL, mAppSerial);
						break;
					case CARD_NUM:
						mXmlCmdKey.add("cardnum");
						/** 开始执行配置文件指令 */
						NFCCardManager.excuXmlCmd();
						budle.putString(CARD_NUM, mCardNum);
						break;
					case CARD_BLANCE:
						mXmlCmdKey.add("blance");
						/** 开始执行配置文件指令 */
						NFCCardManager.excuXmlCmd();
						budle.putString(CARD_BLANCE, mBlance);
						break;
					case CARD_TERMINALNO:
						mXmlCmdKey.add("terminalno");
						/** 开始执行配置文件指令 */
						NFCCardManager.excuXmlCmd();
						budle.putString(CARD_TERMINALNO, mTerminalNo);
						break;
					case CARD_LAST_RECODE:
						/** 最后一条记录 */
						budle.putString(CARD_LAST_RECODE, readLastRecord());
						break;
					case CARD_LOCAL_RECODE:
						budle.putParcelableArrayList(CARD_LOCAL_RECODE,
								getmLocalRecode());
						break;
					case CARD_REMOTE_RECODE:
						budle.putParcelableArrayList(CARD_REMOTE_RECODE,
								getmOtherRecode());
						break;
					case CARD_CHARGE_RECODE:
						budle.putParcelableArrayList(CARD_CHARGE_RECODE,
								getmChargeRecode());
						break;
					}
				}
			}
			msg.setData(budle);
			return msg;
		}

	}
	

	/**
	 * 获取指令应答解析后余额
	 * @return
     */
	public String getCardBalance(String balance){
	
      return  FormatUtils.formatAmount(balance);
	}
	
	
	
    /**
     * 二次格式解析后的cardNum
     * @return
     */
    public String getCardNum(String cardNum){
        return  cardNum;
    }
    
    
	@Override
	public String getCardSerial(String serial) {
		// TODO Auto-generated method stub
	       return  serial;
	}
    
	/**
	 * 读取卡内最后一条记录；
	 * 
	 * @return
	 */
	private String readLastRecord() {
		String resp = "8800";
		String file = "00A40000020018";
		byte[] temResp = NFCCardManager.execuCmd(file);
		String selectFileResult = ByteUtils.getHex(temResp);
		if (selectFileResult != null && selectFileResult.endsWith("9000")) {// 选择文件成功
			String apduStr = null;
			apduStr = "00B2010417";
			byte[] lastResp = NFCCardManager.execuCmd(apduStr);
			resp = ByteUtils.getHex(lastResp);
		}
		return resp;
	}

/*	*//**
	 * 读取常熟交易记录 (本地、异地、充值)
	 *//*
    private ArrayList<TradeRecord> readRecord(String fileResult) {
        final ArrayList<TradeRecord> list = new ArrayList<TradeRecord>();
        if (fileResult != null && fileResult.endsWith("9000")) {// 选择文件成功
            String apduStr = null;
    			for (int i = 1; i < 11; i++) {// 顺序读取01-0A号记录
    				apduStr = "00B2" + ByteUtils.getHex(new Byte("" + i)) + "0417";
    			    byte[] temResp = NFCCardManager.execuCmd(apduStr);
                    String resp = ByteUtils.getHex(temResp);
    				if (TextUtils.isEmpty(resp) || !resp.endsWith("9000"))
    					break;
    				if (resp.equals("00000000000000000000000000000000000000000000009000"))
    					break;
    				list.add(new TradeRecord(resp));
    			}
   
        }
        return list;
    }*/


	/**
	 * 读取交易记录 (本地、异地、充值)
	 */
	private ArrayList<TradeRecord> readRecord(String fileResult,String tradeType) {
		final ArrayList<TradeRecord> list = new ArrayList<TradeRecord>();
		if (fileResult != null && fileResult.endsWith("9000")) {// 选择文件成功
			String apduStr = null;
			for (int i = 1; i < 11; i++) {// 顺序读取01-0A号记录
				apduStr = "00B2" + ByteUtils.getHex(new Byte("" + i)) + "0417";
				byte[] temResp =  NFCCardManager.execuCmd(apduStr);
				String resp = ByteUtils.getHex(temResp);
				if (TextUtils.isEmpty(resp) || !resp.endsWith("9000"))
					break;
				if (resp.equals("00000000000000000000000000000000000000000000009000"))
					break;
				TradeRecord tr = new TradeRecord(resp);
				if (null != tr && null != tr.tradeType
						&& tr.tradeType.equalsIgnoreCase(tradeType)) {
					list.add(tr);
				}
			}

		}
		return list;
	}


	/**
	 * terminalNo解析
	 * 
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

	private String getFormatAppSerial(String resp, String code) {
		if ((resp.endsWith("9000")) && (code.endsWith("9000"))) {
			mAppSerial = code.substring(0, code.length() - 4)
					+ resp.substring(0, resp.length() - 4);
		} else {
			mAppSerial = null;
		}
		return mAppSerial;
	}

	
	private String getFormatCardNum(String resp, String code) {
		if ((resp.endsWith("9000")) && (code.endsWith("9000"))) {
			String sn = mCardNum.substring(0, mCardNum.length() - 4);
			mCardNum = code.substring(0, code.length() - 4) + sn;
		} else {
			mCardNum = null;
		}
		return mCardNum;
	}

	/**
	 * 余额解析
	 * 
	 * @param resp
	 * @return
	 */
	private String getBalance(String resp) {
		String balance = "";
		if (resp.endsWith("9000"))
			balance = resp.substring(0, resp.length() - 4);// 保证新卡金额为0//
															// 而不是默认的80000000
		return balance;
	}

	  /**
     * 二次格式解析后余额
     * @return
     */
    public String getCardBlance(String balance){
        return  FormatUtils.formatAmount(balance);
    }

	/**
	* 获取指令应答后解析为分的余额
	 * @return
	*/
	public String getCardBalanceFen(String balance){
		long amountValue = Long.parseLong(balance, 16);
		return  String.valueOf(amountValue);
	}


	/**
	 * 执行APDUList
	 *
	 * @param
	 * @return
	 */
	public CardPOR transceiveApduList(ArrayList<String> apduList, String aid) {
		ZLogCard.i("executeApdus");
		String resp = "";
		CardPOR cardPOR = new CardPOR();
		int succedNum = 0;
		for (String apdu : apduList) {
			byte[] temResp =   NFCCardManager.execuCmd(apdu);
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



	// 存在问题，会导致无法在个人化时采用selectByName，只能采用selectByDF（SFI）
	private boolean isSelectCommand(String apdu) {
		/**
		 * 选择指令是以00A404开头的
		 */
		boolean flag = false;
//		String apduStr = ByteUtils.getHex(apdu);
		// ==========================================
		// @date 2015-09-23 选择实例AID或SSD的aid时才开通道，实例AID是16字节
		// if (apduStr.startsWith("00A404")) {
		// 00A4040010D1560000400000535900204000000000
		if (apdu.startsWith("00A4040010")) {
			// ==========================================
			flag = true;
		}
		return flag;
	}
    
    /**
     * 执行深圳通APDU指令
     * flag 0：下载和删除： 2：充值 加flag区分充值或者下载不同流程传递的参数
     * */
    public CardPOR transceiveApduList(ArrayList<String> apduList, int flag, String aid) {

        return transceiveApduList(apduList,aid);
    }
	
	/**
	 * 读取卡上记录
	 */
	private void readRecode(String group) {
		mXmlCmdKey.clear();
		mXmlCmdKey.add(group);
		/** 开始执行配置文件指令 */
		NFCCardManager.excuXmlCmd();
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

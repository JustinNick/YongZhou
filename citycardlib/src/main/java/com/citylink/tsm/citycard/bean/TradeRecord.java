package com.citylink.tsm.citycard.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TradeRecord implements Parcelable{

	public String	serialNum;
	public String	amount;
	public String	tradeType;
	public String	terminalNum;
	public String	tradeDate;
	public String	tradeTime;
    public String   resp;
	/*
	 * TODO 根据APDU返回的记录响应解析成记录对象
	 */
	public TradeRecord(String resp) {
		this.resp = resp;
		if (resp.length() < 23 * 2) return;
		resp.replaceAll(" ", "");
		// 2字节电子钱包交易序号＋3字节预留值＋4字节交易金额＋1字节交易类型＋6字节交易终端编号＋4字节交易日期＋3字节交易时间
		serialNum = resp.substring(0, 2 * 2);
		amount = resp.substring((2 + 3) * 2, (2 + 3 + 4) * 2);
		tradeType = resp.substring((2 + 3 + 4) * 2, (2 + 3 + 4 + 1) * 2);
		terminalNum = resp.substring((2 + 3 + 4 + 1) * 2, (2 + 3 + 4 + 1 + 6) * 2);
		Log.d("terminalNum-->", terminalNum);
		tradeDate = resp.substring((2 + 3 + 4 + 1 + 6) * 2, (2 + 3 + 4 + 1 + 6 + 4) * 2);
		tradeTime = resp.substring((2 + 3 + 4 + 1 + 6 + 4) * 2, (2 + 3 + 4 + 1 + 6 + 4 + 3) * 2);
		Log.d("tradeTime-->", tradeTime);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(resp);
	}

	
	public static final Creator<TradeRecord> CREATOR = new Creator<TradeRecord>() {
		@Override
		public TradeRecord createFromParcel(Parcel in) {

			return new TradeRecord(in.readString());
		}

		@Override
		public TradeRecord[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TradeRecord[size];
		}
	};

}

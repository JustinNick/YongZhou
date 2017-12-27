package com.citylink.tsm.citycard.bean;

//@XStreamAlias(Value.XMLNS + "CardPOR")
public class CardPOR {
	//@XStreamAlias(Value.XMLNS + "APDUSum")
	private String	APDUSum		= "0";
	//@XStreamAlias(Value.XMLNS + "LastApduSW")
	private String	LastApduSW	= "8800";	// 默认错误，避免打开通道出错之类的没有resp的情况
	//@XStreamAlias(Value.XMLNS + "LastData")
	private String	LastData;
	//@XStreamAlias(Value.XMLNS + "LastApdu")
	private String	LastApdu;

	public String getAPDUSum() {
		return APDUSum;
	}

	public void setAPDUSum(String aPDUSum) {
		APDUSum = aPDUSum;
	}

	public String getLastApduSW() {
		return LastApduSW;
	}

	public void setLastApduSW(String lastApduSW) {
		LastApduSW = lastApduSW;
	}

	public String getLastData() {
		return LastData;
	}

	public void setLastData(String lastData) {
		LastData = lastData;
	}

	public String getLastApdu() {
		return LastApdu;
	}

	public void setLastApdu(String lastApdu) {
		LastApdu = lastApdu;
	}

}

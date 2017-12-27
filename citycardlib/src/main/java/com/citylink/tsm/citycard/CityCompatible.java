package com.citylink.tsm.citycard;

import android.text.TextUtils;

import com.citylink.tsm.citycard.bean.CardPOR;
import com.citylinkdata.cardnfc.BaseCityCard;

import java.util.ArrayList;


/**
 * IC、Smart citylinknfc.jar包
 * 针对特定业务需求
 * 固该类兼容性处理
 * 以达到业务层统一（注：业务层和UI层不应出现具体城市实现类）
 * 方便维护扩展（遵循OCP规则）
 */
  public abstract class CityCompatible extends BaseCityCard {
	/**
	 * 执行APDUList
	 * @param apduList
	 * @return
	 */
	public abstract CardPOR transceiveApduList(ArrayList<String> apduList, String aid);

	/**
	 * 执行APDU指令
	 * flag 0：下载和删除： 2:充值 加flag区分充值或者下载不同流程传递的参数
	 * */
	public abstract CardPOR transceiveApduList(ArrayList<String> apduList, int flag,String aid);

	/**
	 * sw应答结果
	 * @param resp
	 * @return
     */
	public String getSW(String resp) {
		if (TextUtils.isEmpty(resp))
			return "8800";
		if (resp.length() < 4)
			return resp;
		return resp.substring(resp.length() - 4);
	}


	/**
	 * 判断卡类型
	 * 0x03是正常卡
	 * 0x00是未开通卡
	 * 0x09是黑名单卡
	 * 其余的异常卡
	 */
	public String  jugementCardType(){
		return  null;
	}


	/**
	 * 获取解析后的Serial
	 * @return
     */
	public abstract String  getCardSerial(String serial);


	/**
	 * 获取指令应答解析后余额
	 * @return
     */
	public abstract String getCardBalance(String balance);
	/**
	 * 获取指令应答解析的分单位值。
	 * */
	public abstract String getCardBalanceFen(String balance);
	/**
	 * 获取指令应答解析后余额
	 * @return
	 */
	public abstract String getCardNum(String cardNum);

	/**
	 *  判断AID通道是否可以打开；
	 * @return
     */
	public boolean appIsExist(){
		return false;
	}
	
}

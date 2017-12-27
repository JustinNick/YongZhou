package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.citylink.tsm.citycard.CityCompatible;
import com.citylink.tsm.citycard.bean.CardPOR;
import com.citylink.tsm.citycard.cityic.YongzhouIC;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.cardnfc.NFCCardManager;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.APDUBean;
import com.citylinkdata.yongzhou.bean.ReAPDUBean;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.APDUPresenter;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.TimeUtil;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 * NFC充值
 */
public class NFCRechargeResultActivity extends BaseAppActivity implements APDUPresenter.OnAPDUPresenter {
    private ImageView mIvMoveCard;

    /**
     * 某些公用方法建议放在基类里面
     */
    private PendingIntent mPendingIntent;
    private NfcAdapter mNfcAdapter;
    private TextView mTvWallet;
    private TextView mTvChargeStatuF;
    /**
     * 贴卡处理
     */
    private Intent mTagIntent = null;
    private int i = 0;
    private APDUPresenter apduPresenter;

    private String amount, tradePwd, bankid, tn;
    private String sequenceId = "";

    String apduSum = "0";
    String lastApduSW = "";
    String lastData = "";
    String lastApdu = "";
    private TextView tvTips;

    private boolean isReadInfo, isRechargeCard;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {
            /**让你的应用程序来处理这个Intent对象*/
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
                    NFCCardManager.FILTERS, NFCCardManager.TECHLISTS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null && mNfcAdapter.isEnabled())
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nfcrecharge_result;
    }

    @Override
    protected void initView() {
        tvTips = (TextView) findViewById(R.id.tv_tips);
        setTipsText(getResources().getString(R.string.please_paste_card));

        mIvMoveCard = (ImageView) findViewById(R.id.move_card_imageview);
        mTvChargeStatuF = (TextView) findViewById(R.id.tv_charge_status);
        mTvWallet = (TextView) findViewById(R.id.tv_wallet);
        TitleView titleView = (TitleView) findViewById(R.id.title_view);

        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick() {
//                一期不做退款功能
//                UiHelp.jumpRefundsNoVertify(NFCRechargeResultActivity.this);
            }
        });

        startCardAnimal();

        apduPresenter = new APDUPresenter();
        apduPresenter.setOnAPDUPresenter(this);

        amount = getIntent().getStringExtra("amount");
        tradePwd = getIntent().getStringExtra("tradePwd");
        bankid = getIntent().getStringExtra("bankid");
        tn = getIntent().getStringExtra("tn");

        mTvWallet.setText("+" + amount);


        /**设备NFC检查*/
        if (CheckNFC()) {
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            mTagIntent = getIntent();
            onNewIntent(mTagIntent);
        }
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(getBaseContext());
//        Intent intent = new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        mPendingIntent = PendingIntent.getActivity(this.getBaseContext(),0,intent,0);
//        mTagIntent = getIntent();
//        onNewIntent(getIntent());
    }


    /**
     * NFC功能检查
     */
    private boolean CheckNFC() {
        /**获取默认的NFC控制器(目前几乎所有ROM都一个适配器)*/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            showToast("设备不支持NFC！");
            return false;
        } else if (!mNfcAdapter.isEnabled()) {
            showToast("请在系统设置中先启用NFC功能！");
            return false;
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (i == 1) {

            mTagIntent = intent;
            /**NFC 卡片功能初始化  太仓 citycode 2154    永州 4250*/
            NFCCardManager.initNFCCard(intent, SPString.CITY_CODE, null);

            /**读取卡余额*/
            if (!isReadInfo) {
                readInfo();
            } else {
                if (isRechargeCard) {
                    rechargeCard();
                }


            }
        } else {
            i++;
//            rechargeCard();
        }
    }

    /**
     * 读取卡号余额
     */

    private void readInfo() {
        setTipsText(getResources().getString(R.string.nfc_recharging));
        showLoading(getResources().getString(R.string.recharge_running));
        mTvChargeStatuF.setText(R.string.nfc_recharging_tips1);
        isReadInfo = true;

        /**需要读取卡上的数据   把对于的key加入数组 */
        String[] arg0 = {BaseCityCard.CARD_BLANCE, BaseCityCard.CARD_NUM,
                BaseCityCard.CARD_SERIAL, BaseCityCard.CARD_LOCAL_RECODE, BaseCityCard.CARD_CHARGE_RECODE, BaseCityCard.CARD_LAST_RECODE};

        /**返回卡上数据 budle */
        Bundle cardundle = getCardInfo(arg0);

        /**类似key-value获取*/
        String preBalance = cardundle.getString(BaseCityCard.CARD_BLANCE);
        String cardNum1 = cardundle.getString(BaseCityCard.CARD_NUM);
        String appSerial = cardundle.getString(BaseCityCard.CARD_SERIAL);

        if (cardNum1 != null && preBalance != null) {
            CityCompatible cityCompatible = (CityCompatible) getBaseCityCard();
            cardNum = cityCompatible.getCardNum(cardNum1);
            balance = cityCompatible.getCardBalance(preBalance);
            lastRecord = String.valueOf(cardundle.getParcelableArray(BaseCityCard.CARD_LAST_RECODE));

            if (!cardNum.substring(0, 4).equals(SPString.CITY_CODE)) {
                showToast("暂时不支持该城市卡片充值!");
                return;
            }

//            String reqCode, String sequenceId, String cardNo, String money,  String chargePwd, String payChannel, String tn,
//                    String cardBalance, String lastRecord, String apduSum, String lastApduSW, String lastData,
//                    String lastApdu, String sigNature

            CardPOR cardPOR = cityCompatible.transceiveApduList(apdulist, 0, SPString.CURRENTAPPAID);
//            lastApduSW = cardPOR.getLastApduSW();
            lastData = cardPOR.getLastData();
//            lastApdu = cardPOR.getLastApdu();
//            apduSum = cardPOR.getAPDUSum();
            APDUBean apduBean;
            //进行可疑交易验证
            if(isFirst) {
                apduBean = new APDUBean("1010", sequenceId, cardNum, amount, tradePwd, bankid, tn,
                        balance, lastRecord, apduSum, lastApduSW, lastData,
                        lastApdu, tradePwd);
            }else {
                //进来直接进行充值调此方法
                apduBean = new APDUBean("1005", sequenceId, cardNum, amount, tradePwd, bankid, tn,
                        balance, lastRecord, apduSum, lastApduSW, lastData,
                        lastApdu, tradePwd);
            }
            apduPresenter.getAPDU(apduBean);

        }

    }


    /**
     * 获取某城市对于IC还是SIM卡
     *
     * @return
     */
    public BaseCityCard getBaseCityCard() {
        BaseCityCard baseCity = null;
        if (NFCCardManager.isNFCardIcTag() || NFCCardManager.isIcFlag()) {
            baseCity = NFCCardManager.getCityCard(); //IC卡
        }
        return baseCity;
    }


    /**
     * 获取卡上信息
     *
     * @param key
     * @return
     */
    public Bundle getCardInfo(String[] key) {
        BaseCityCard cityCard = getBaseCityCard();
        if (cityCard == null)
            return new Bundle();
        else {
            Message msg = cityCard.getCardInfo(key);
            return msg.getData();
        }
    }


    /**
     * 开启充值卡移动动画
     */
    private AnimationSet animationSet;

    private void startCardAnimal() {
        animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.translate);

        mIvMoveCard.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvMoveCard.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationSet.setAnimationListener(null);
        animationSet = null;

    }


    /**
     * 成功
     *
     * @param reAPDUBean
     */
    ArrayList<String> apdulist = new ArrayList<>();
    //判断是否为可疑验证进入以下方法
    private boolean isFirst = true;

    @Override
    public void onReChargeSuccess(final ReAPDUBean reAPDUBean) {
//        if (!TextUtils.isEmpty(reAPDUBean.getSequenceId())) {
//            sequenceId =reAPDUBean.getSequenceId();
//            readInfo();
//        }

        //实时显示充值状态
        handler.post(new Runnable() {
            @Override
            public void run() {

                mTvChargeStatuF.setText(reAPDUBean.getRespMsg());
            }
        });




        //如果第一次进入此方法 ，则为可疑交易验证接口进入
        if(isFirst){
            //确保此方法只进入一次
            isFirst = false;
            //充值卡
            readInfo();
            return;
        }

        sequenceId = reAPDUBean.getSequenceId();
        ArrayList<ReAPDUBean.ApduListBean> apdus = (ArrayList<ReAPDUBean.ApduListBean>) reAPDUBean.getApduList();

        if (apdus.size() != 0) {
            apdulist.clear();
            apdulist.addAll(getApduList(apdus));
            isRechargeCard = true;
            rechargeCard();
        } else {
            closeLoading();
            float currentBalance = Float.parseFloat(balance.replace("元", "")) * 100 + Float.parseFloat(amount.replace("元", "")) * 100;
            double currentBalanceDou = currentBalance / 100.00;
            Intent intent = new Intent(this, RechargeSuccessActivity.class);
            intent.putExtra("issuccess", true).putExtra("type", "0");
            intent.putExtra("cardnum", cardNum);
            intent.putExtra("balance", balance);
            intent.putExtra("amount", amount);
            intent.putExtra("balance_current", String.valueOf(currentBalanceDou) + "元");
            intent.putExtra("time", TimeUtil.getCurrentTime(TimeUtil.FORMAT_DATE_TIME));
            intent.putExtra("err_message", "");
            startActivity(intent);
            AppManager.getAppManager().finishActivity(this);
        }

    }

    private ArrayList<String> getApduList(ArrayList<ReAPDUBean.ApduListBean> apdulist) {
        ArrayList<String> list = new ArrayList<>();
        for (ReAPDUBean.ApduListBean apduBean : apdulist) {
            list.add(apduBean.getApdu());
        }
        return list;
    }

    /**
     * 充值失败
     *
     * @param respmsg
     */
    @Override
    public void onReChargeFailed(final String respmsg) {
        closeLoading();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mTvChargeStatuF.setText(respmsg);
            }
        });
        Intent intent = new Intent(this, RechargeSuccessActivity.class);
        intent.putExtra("issuccess", false).putExtra("type", "0");
        intent.putExtra("cardnum", cardNum);
        intent.putExtra("balance", balance);
        intent.putExtra("amount", amount);
        intent.putExtra("balance_current", balance);
        intent.putExtra("time", TimeUtil.getCurrentTime(TimeUtil.FORMAT_DATE_TIME));
        intent.putExtra("err_message", respmsg);
        startActivityForResult(intent, 0);
    }

    String cardNum, balance, lastRecord;

    private void rechargeCard() {

        CityCompatible cityCompatible = (CityCompatible) getBaseCityCard();

        CardPOR cardPOR = cityCompatible.transceiveApduList(apdulist, 0, SPString.CURRENTAPPAID);
        lastApduSW = cardPOR.getLastApduSW();
        lastData = cardPOR.getLastData();
        lastApdu = cardPOR.getLastApdu();
        apduSum = cardPOR.getAPDUSum();

//            String reqCode, String sequenceId, String cardNo, String money,  String chargePwd, String payChannel, String tn,
//                    String cardBalance, String lastRecord, String apduSum, String lastApduSW, String lastData,
//                    String lastApdu, String sigNature
        APDUBean apduBean = new APDUBean("1005", sequenceId, cardNum, amount, tradePwd, bankid, tn,
                balance, lastRecord, apduSum, lastApduSW, lastData,
                lastApdu, tradePwd
        );

        apduPresenter.getAPDU(apduBean);
        isRechargeCard = false;
    }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            //将以下状态置为初始状态
            sequenceId = "";
            isFirst = true;
            apduSum = "0";
            lastApduSW = "";
            lastData = "";
            lastApdu = "";
            i = 1;
            isReadInfo = false;//重新读卡信息进行充值
            setTipsText(getResources().getString(R.string.please_paste_card));
            mTvChargeStatuF.setText(getResources().getString(R.string.nfc_recharging_tips1));
        }
        if (requestCode == 2) {
            finish();
        }
    }


    private void setTipsText(String msg) {
        tvTips.setText(msg);
        tvTips.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTips.setSingleLine(true);
        tvTips.setSelected(true);
        tvTips.setFocusable(true);
        tvTips.setFocusableInTouchMode(true);
    }
}

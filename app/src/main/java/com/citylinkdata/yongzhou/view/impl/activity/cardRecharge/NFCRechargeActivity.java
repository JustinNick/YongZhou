package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.citylink.tsm.citycard.CityCompatible;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.cardnfc.NFCCardManager;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 * NFC充值
 */
public class NFCRechargeActivity extends BaseAppActivity {
    private ImageView mIvMoveCard;
    private TextView tvTips;

    /**某些公用方法建议放在基类里面*/
    private PendingIntent mPendingIntent;
    private NfcAdapter mNfcAdapter;
    /**贴卡处理*/
    private Intent mTagIntent = null;
    private int i=0;

    @Override
    protected void onResume() {
        super.onResume();

        if(CheckNFC() ){
            if(mPendingIntent==null) {
                mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                        getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                mTagIntent = getIntent();
                onNewIntent(mTagIntent);
            }

            if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {
                /**让你的应用程序来处理这个Intent对象*/
                mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
                        NFCCardManager.FILTERS, NFCCardManager.TECHLISTS);
            }
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
        return R.layout.activity_nfcrecharge;
    }

    @Override
    protected void initView() {
        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick() {
                UiHelp.jumpNFC(NFCRechargeActivity.this);
            }
        });
        mIvMoveCard = (ImageView) findViewById(R.id.move_card_imageview);
        startCardAnimal();

        /**设备NFC检查*/
        if(CheckNFC() ){
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

        tvTips = (TextView) findViewById(R.id.tv_tips);
        setTipsText(getResources().getString(R.string.please_paste_card));
    }


    /**
     * NFC功能检查
     */
    private boolean CheckNFC(){
        /**获取默认的NFC控制器(目前几乎所有ROM都一个适配器)*/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            showToast("设备不支持NFC！");
            return false;
        }else if (!mNfcAdapter.isEnabled()) {
            showToast("请在系统设置中先启用NFC功能！");
            return false;
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(i>0) {

            mTagIntent = intent;
            /**NFC 卡片功能初始化  太仓 citycode 2154    永州 4250*/
            NFCCardManager.initNFCCard(intent, SPString.CITY_CODE, null);

            /**读取卡余额*/
            readInfo();
        }else {
            i++;
        }
    }

    /**
     * 读取卡号余额
     */
    private void  readInfo(){
        //读卡中跑马灯提示
         setTipsText(getResources().getString(R.string.reading_the_card_tips));

        /**需要读取卡上的数据   把对于的key加入数组 */
        String[] arg0 = {BaseCityCard.CARD_BLANCE, BaseCityCard.CARD_NUM,
                BaseCityCard.CARD_SERIAL,BaseCityCard.CARD_LOCAL_RECODE,BaseCityCard.CARD_CHARGE_RECODE,"card_enabled"};

        /**返回卡上数据 budle */
        Bundle cardundle = getCardInfo(arg0);

        /**类似key-value获取*/
        String preBalance = cardundle.getString(BaseCityCard.CARD_BLANCE);
        String cardNum= cardundle.getString(BaseCityCard.CARD_NUM);
        String appSerial = cardundle.getString(BaseCityCard.CARD_SERIAL);
        boolean cardEnabled = cardundle.getBoolean("card_enabled");
        if(!cardEnabled){
            showToast("读卡错误或者此卡已经被停用!");
            return;
        }
        if(!cardNum.substring(0,4).equals(SPString.CITY_CODE)){
            showToast("暂时不支持该城市卡片充值!");
            return;
        }

        //获取到数据后跳转充值页面
        if(cardNum != null  && preBalance != null) {
            cardNum = ((CityCompatible) getBaseCityCard()).getCardNum(cardNum);
            String balance = ((CityCompatible) getBaseCityCard()).getCardBalance(preBalance);

            Intent intent = new Intent(this,RechargeActivity.class);
            intent.putExtra("cardNum",cardNum);
            intent.putExtra("balance",balance);
            startActivity(intent);
            AppManager.getAppManager().finishActivity(this);
        }

    }


    /**
     * 获取某城市对于IC还是SIM卡
     * @return
     */
    public BaseCityCard getBaseCityCard(){
        BaseCityCard baseCity  = null;
        if(NFCCardManager.isNFCardIcTag() || NFCCardManager.isIcFlag()){
            baseCity = NFCCardManager.getCityCard(); //IC卡
        }
        return baseCity;
    }


    /**
     * 获取卡上信息
     * @param key
     * @return
     */
    public Bundle getCardInfo(String[] key){
        BaseCityCard cityCard = getBaseCityCard();
        if(cityCard ==null)
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


    private void setTipsText(String msg){
        tvTips.setText(msg);
        tvTips.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTips.setSingleLine(true);
        tvTips.setSelected(true);
        tvTips.setFocusable(true);
        tvTips.setFocusableInTouchMode(true);
    }
}

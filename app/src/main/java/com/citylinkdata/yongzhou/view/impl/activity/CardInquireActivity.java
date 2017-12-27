package com.citylinkdata.yongzhou.view.impl.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.citylink.tsm.blecitycard.blecard.BleCardManager;
import com.citylink.tsm.citycard.CityCompatible;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylink.tsm.citycard.cityic.UnionIC;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.cardnfc.NFCCardManager;
import com.citylinkdata.cardnfc.b;
import com.citylinkdata.cardnfc.smartcard.NFCSmartCardManager;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.BTAdapter;
import com.citylinkdata.yongzhou.adapter.CardTabAdapter;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.cardinquire.BindInquireFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.cardinquire.PasteInquireFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.cardinquire.ReadInquireFragment;
import com.nci.tkb.btjar.base.IConnectCallBack;
import com.nci.tkb.btjar.base.IScanBlueTooth;
import com.nci.tkb.btjar.bean.ScanDeviceBean;
import com.nci.tkb.btjar.exception.BTException;
import com.nci.tkb.btjar.helper.BlueToothHelper;
import com.nci.tkb.btjar.utils.AppContextUtil;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 卡片查询
 */

public class CardInquireActivity extends BaseAppActivity  {
    private String TAG = "CardInquireActivity";
    private TabLayout mTableLayout;
    private String[] mStrs;
    private Fragment mBindFragment, mPasteFragment, mReadFragment;
    private Fragment[] mFragments;
    private CardTabAdapter mCardTabAdapter;
    private ViewPager mViewPager;
    private TitleView mTitleView;
    private Context ctx;

    TextView mBluetoothInfo;
    //NFC
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private Intent mTagIntent;

    private ArrayList<TradeRecord> mChargeRecordList = null;//充值记录
    private ArrayList<TradeRecord> mLocalRecordList=null;//消费记录

    private int currentTab;

    private NfcAdapter mNfcAdapter;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_inquire;
    }

    @Override
    protected void initView() {
        isFragmentActivity=true;

        ctx = getContext();
        mTitleView = (TitleView) findViewById(R.id.title_view);
        mTableLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);


        mBindFragment = new BindInquireFragment();
        mPasteFragment = new PasteInquireFragment();
        mReadFragment = new ReadInquireFragment();

        mStrs = new String[]{getResources().getString(R.string.bindInquire),getResources().getString(R.string.paseInquire),getResources().getString(R.string.readInquire)};
        mFragments = new Fragment[]{mBindFragment, mPasteFragment, mReadFragment};

        //设置TabLayout的模式
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        int size = mFragments.length;
        for(int i=0;i<size;i++){
            mTableLayout.addTab(mTableLayout.newTab().setText(mStrs[i]));
        }

        mCardTabAdapter = new CardTabAdapter(getSupportFragmentManager(),mFragments,mStrs);

        //viewpager加载adapter
        mViewPager.setAdapter(mCardTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mTableLayout.setupWithViewPager(mViewPager);
        //tab_FindFragment_title.set
        //设备NFC接入
        nfcAdapter = NfcAdapter.getDefaultAdapter(getBaseContext());
        Intent intent = new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this.getBaseContext(),0,intent,0);
        mTagIntent = getIntent();
        onNewIntent(getIntent());

        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab=tab.getPosition();
                //切换标题图标显示为信号图标
                mTitleView.setRightImageViewResouse(R.drawable.nfc_swich);
                switch (currentTab){
                    case 0://绑卡查询
                        closeLoading();
                        ((ReadInquireFragment)mReadFragment).diConnectDev();
                        break;
                    case 1://NF0查询
                        CheckNFC();
                        closeLoading();
                        ((ReadInquireFragment)mReadFragment).diConnectDev();
                        break;
                    case 2://蓝牙查询
                        //当fragment可见时，调用搜索蓝牙方法
                        showLoading("设备搜索中...");//显示等待层
                        ((ReadInquireFragment)mReadFragment).initHelper();
                        //切换标题图标显示为蓝牙图标
                        mTitleView.setRightImageViewResouse(R.drawable.bluetooth_switch);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mTitleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick() {
                if(currentTab==2){

                    UiHelp.jumpBlueTooth(CardInquireActivity.this);
                }else{

                    UiHelp.jumpNFC(CardInquireActivity.this);

                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(nfcAdapter!=null){
            nfcAdapter.enableForegroundDispatch(this,pendingIntent, NFCCardManager.FILTERS,NFCCardManager.TECHLISTS);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(nfcAdapter!=null){
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        int showingTabIndex= mTableLayout.getSelectedTabPosition();
        if(showingTabIndex==1) {//Tab处于帖卡查询的选择
            L.e(TAG,"接收到读卡动作");
            mTagIntent = intent;
            //2155常熟
            NFCCardManager.initNFCCard(mTagIntent, SPString.CITY_CODE,null);
            showNFCData();
        }
    }
    private void showNFCData(){
        String[] arg0 = {BaseCityCard.CARD_BLANCE, BaseCityCard.CARD_NUM,BaseCityCard.CARD_DATE,
                BaseCityCard.CARD_SERIAL,BaseCityCard.CARD_LOCAL_RECODE,BaseCityCard.CARD_CHARGE_RECODE,BaseCityCard.CARD_REMOTE_RECODE,"card_enabled"};
        Bundle cardundle = getCardInfo(arg0);
        String preBalance = cardundle.getString(BaseCityCard.CARD_BLANCE);
        String cardNum= cardundle.getString(BaseCityCard.CARD_NUM);
        String appSerial = cardundle.getString(BaseCityCard.CARD_SERIAL);
        String cardExtendDate = cardundle.getString(BaseCityCard.CARD_DATE);
        boolean cardEnabled = cardundle.getBoolean("card_enabled");
        if(!cardEnabled){
            showToast("此卡已经被停用或者该卡不属于该城市通卡。");
            return;
        }
        mChargeRecordList=cardundle.getParcelableArrayList(BaseCityCard.CARD_CHARGE_RECODE);
        mLocalRecordList = cardundle.getParcelableArrayList(BaseCityCard.CARD_LOCAL_RECODE);

        if(cardNum != null  && preBalance != null) {
            cardNum = ((CityCompatible) getBaseCityCard()).getCardNum(cardNum);
            String balance = ((CityCompatible) getBaseCityCard()).getCardBalance(preBalance);
            StringBuffer buffer = new StringBuffer();

            buffer.append("卡号：" + cardNum + "\n");
            buffer.append("余额：" + balance + "\n");
            buffer.append("充值数量：" + mChargeRecordList.size());
            //buffer.append("消费数量：" + mLocalRecordList.size());
//            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
            //mChargeRecordList.addAll(mLocalRecordList);
            Intent intent=new Intent(this,CardRecodeActivity.class);
            intent.putExtra("Card_Num",cardNum);
            intent.putExtra("Card_Balance",balance);
            intent.putExtra("Card_ExtendDate",cardExtendDate);
            intent.putExtra("IsBle",false);
            intent.putParcelableArrayListExtra("Card_Logs",mChargeRecordList);
            //intent.putParcelableArrayListExtra("Card_Local_Records",mLocalRecordList);
            startActivity(intent);
        }else{
            Toast.makeText(this, "并非所属城市的卡片。", Toast.LENGTH_SHORT).show();
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
    private boolean CheckNFC()
    {
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(ctx);
        if (this.mNfcAdapter == null)
        {
            showToast("设备不支持NFC功能！");
            return false;
        }
        if (!this.mNfcAdapter.isEnabled())
        {
            showToast("请在系统设置中先启用NFC功能！");
            return false;
        }
        return true;
    }

}

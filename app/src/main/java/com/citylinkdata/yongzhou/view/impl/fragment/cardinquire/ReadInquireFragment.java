package com.citylinkdata.yongzhou.view.impl.fragment.cardinquire;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.citylink.tsm.blecitycard.BleCityCompatible;
import com.citylink.tsm.blecitycard.blecard.BleBaseCityCard;
import com.citylink.tsm.blecitycard.blecard.BleCardManager;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.userview.DevDialog;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.activity.CardInquireActivity;
import com.citylinkdata.yongzhou.view.impl.activity.CardRecodeActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;
import com.nci.tkb.btjar.base.IConnectCallBack;
import com.nci.tkb.btjar.base.IScanBlueTooth;
import com.nci.tkb.btjar.bean.ScanDeviceBean;
import com.nci.tkb.btjar.exception.BTException;
import com.nci.tkb.btjar.helper.BlueToothHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 读卡器查询
 */

public class ReadInquireFragment extends BaseFragment implements IConnectCallBack, IScanBlueTooth {

    private ImageView mIvMoveCard;
    private TextView mBluetoothInfo, mBluetoothState;
    private Handler myHandler = new Handler();
    private static final int READCARD = 0;

    private BlueToothHelper bthHelper;
    private ScanDeviceBean dev;

    private ArrayList<TradeRecord> mChargeRecordList = null;//充值记录
    private ArrayList<TradeRecord> mLocalRecordList=null;//消费记录
    private  boolean hadScanDevs=false;
    /**
     * 读卡状态标记，如果读到卡，则不再读卡
     */
    private boolean isReadCard = false;
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if(!isReadCard) {
                        ReadCard();
                    }

                }

            });

            if(!isReadCard){
                handler.sendEmptyMessageDelayed(READCARD,2000);
            }
            return false;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inquire_read;
    }


    @Override
    protected void initView() {
        mIvMoveCard = (ImageView) contentView.findViewById(R.id.move_card_imageview);
        mBluetoothInfo = (TextView) contentView.findViewById(R.id.tv_bluetooth_info);
        mBluetoothState = (TextView) contentView.findViewById(R.id.tv_bluetooth_state);
        startCardAnimal();
    }


    /**
     * 开启充值卡移动动画
     */
    private void startCardAnimal() {
        final AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.translate);

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
    public void onResume() {
        super.onResume();

    }

    /**
     * 初始化搜索蓝牙设置
     */
    public void initHelper() {
        // TODO Auto-generated method stub
        isDisConnected = false;
        if (bthHelper == null) {
            bthHelper = BlueToothHelper.getInstrance().build(getActivity(), this,this);
            //执行搜索

        }
        scan();
    }

    /**
     * 搜索蓝牙列表
     */
    public void scan() {

        if(bthHelper.isConnected()){
            handler.sendEmptyMessageDelayed(READCARD, 500);
            return;
        }
        showLoading(LoadString.BLUETOOTH_SEARCH);
        bthHelper.scan(10);
    }

    /**
     * 搜索到设备列表后连接第一个蓝牙
     *
     * @param dev
     */
    public void blueToolthConnect(ScanDeviceBean dev) {
        if (null != dev && null != dev.getDevice() && null != dev.getDevice().getAddress()) {
            this.dev = dev;
            if (bthHelper.isConnected()) {
                showToast("设备已经连接");
                return;
            }
            bthHelper.stopScan();
            bthHelper.connect(dev);
        }
    }

    /**
     * 蓝牙设备搜索到并已连接以后
     * 执行下边这个方法 读卡片操作
     */

    public void ReadCard() {
        try {

            if (bthHelper != null) {
                // 卡片复位
                String re = bthHelper.sendApdu("62", 2000, false);
                /**初始化 城市  永州  4250  第三方设备为 通卡宝
                 *执行这句初始化后， UI层读取卡片信息 如：卡号 余额 记录 接口与NFC读写卡片接口几乎一致
                 * 如： 以下  readCardInfo 方法
                 * */

                BleCardManager.initBlueHelper("通卡宝", bthHelper, SPString.CITY_CODE);
                readCardInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取卡片信息
     * <p>
     * 跟NFC读取卡片信息接口几乎一致
     */
    private void readCardInfo() {
        String[] arg0 = {BleBaseCityCard.CARD_BLANCE, BleBaseCityCard.CARD_NUM,BleBaseCityCard.CARD_DATE,
                BleBaseCityCard.CARD_LAST_RECODE,BleBaseCityCard.CARD_CHARGE_RECODE,BleBaseCityCard.CARD_LOCAL_RECODE};//,"card_enabled"
        Bundle cardundle = getBleCardInfo(arg0);

        BleBaseCityCard bleBaseCityCard = getBleBaseCityCard();
        if (cardundle != null && bleBaseCityCard != null) {

            String preBalance = cardundle.getString(BleBaseCityCard.CARD_BLANCE);
            String cardNum = cardundle.getString(BleBaseCityCard.CARD_NUM);
            cardNum = ((BleCityCompatible) bleBaseCityCard).getCardNum(cardNum);
            String blance = ((BleCityCompatible) bleBaseCityCard).getCardBalance(preBalance);
            String cardEnabled = BleCardManager.execuBleCmd("","00B0950801");//查询卡启用状态
            L.e("卡片启用状态："+cardEnabled);
            if(TextUtils.isEmpty(cardEnabled)||!cardEnabled.equals("019000")){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("此卡已经被停用或者该卡不属于本城市通卡。");
                    }
                }) ;
                return;
            }
            if(!cardNum.substring(0,4).equals(SPString.CITY_CODE)){
                showToast("暂时不支持该卡片查询/充值!");
                return;
            }

            String cardExtendDate = BleCardManager.execuBleCmd("","00B0951804");//查询卡有效期//cardundle.getString(BaseCityCard.CARD_DATE);
            if(cardExtendDate.endsWith("9000")){
                cardExtendDate = cardExtendDate.replaceAll("9000","");
            }
            mChargeRecordList=cardundle.getParcelableArrayList(BaseCityCard.CARD_CHARGE_RECODE);
            mLocalRecordList = cardundle.getParcelableArrayList(BaseCityCard.CARD_LOCAL_RECODE);

            isReadCard = true;
            Intent intent=new Intent(mContext,CardRecodeActivity.class);
            intent.putExtra("Card_Num",cardNum);
            intent.putExtra("Card_Balance",blance);
            intent.putExtra("Card_ExtendDate",cardExtendDate);
            intent.putExtra("IsBle",true);
            intent.putParcelableArrayListExtra("Card_Logs",mChargeRecordList);
            //intent.putParcelableArrayListExtra("Card_Local_Records",mLocalRecordList);
            startActivity(intent);
            //关闭窗体
            AppManager.getAppManager().finishActivity();
        }
    }

    /**
     * 获取蓝牙读卡器某城市
     *
     * @return
     */
    public BleBaseCityCard getBleBaseCityCard() {
        BleBaseCityCard baseCity = null;
        baseCity = BleCardManager.getBaseCityBleCard(); //IC卡
        return baseCity;
    }

    /**
     * 通过蓝牙读卡器获取卡上信息
     *
     * @param key
     * @return
     */
    public Bundle getBleCardInfo(String[] key) {
        BleBaseCityCard cityCard = getBleBaseCityCard();
        if (cityCard == null)
            return new Bundle();
        else {
            Message msg = cityCard.getCardInfo(key);
            return msg.getData();
        }
    }

    /**
     * 蓝牙搜索回调
     */
    DevDialog devDialog;
    @Override
    public void scanBlueTooth(List<ScanDeviceBean> list, ScanDeviceBean scanDeviceBean, BTException e) {
        if (null != e)
        {
            showToast(e.getMessage());
            return;
        }
        if(list.size()==1){
            blueToolthConnect(list.get(0));
            return;
        }
        if(devDialog==null) {
            devDialog = new DevDialog(mContext, list);
        }else{
            devDialog.setScanList(list);
        }
        if(list.size()>0){
            hadScanDevs=true;
        }
        devDialog.setOnItemClick(new DevDialog.OnItemClick() {
            @Override
            public void onItemCLick(ScanDeviceBean idev) {
                blueToolthConnect(idev);
            }
        });
        devDialog.show();
    }

    @Override
    public void scanTimeCallBack(int i) {
        if(i==0){
            closeLoading();
            mBluetoothState.setText(mContext.getResources().getString(R.string.bluetooth_reader_search));
            mBluetoothInfo.setText(mContext.getResources().getString(R.string.not_connected_to_the_Bluetooth_reader));
            if (!hadScanDevs) {
                showTimeOutDialog(mContext.getResources().getString(R.string.search_bluetooth_time_out));
            }
        }
    }

    @Override
    public void gattConnect(final ScanDeviceBean scanDeviceBean) {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mBluetoothState.setText("请将一卡通贴在读卡器上");
                mBluetoothInfo.setText("已连接：" + scanDeviceBean.getDevice().getName());
                handler.sendEmptyMessage(READCARD);

                closeLoading();

            }
        }, 1000);
    }


    @Override
    public void gattDisconnect(ScanDeviceBean scanDeviceBean) {
        closeLoading();
        mBluetoothState.setText(mContext.getResources().getString(R.string.bluetooth_reader_search));
        mBluetoothInfo.setText(mContext.getResources().getString(R.string.not_connected_to_the_Bluetooth_reader));
        showTimeOutDialog(mContext.getResources().getString(R.string.bluetooth_disconnected_tips));

    }

    @Override
    public void connectError(BTException e) {
        showToast(e.getMessage());
    }

    @Override
    public void devBondNone(ScanDeviceBean scanDeviceBean) {

    }

    @Override
    public void devBonding(ScanDeviceBean scanDeviceBean) {

    }

    @Override
    public void devBonded(ScanDeviceBean scanDeviceBean) {

    }



    /**
     * 蓝牙连接异常提示dialog  超时、断开连接
     * @param content  提示信息
     */
    private void showTimeOutDialog(String content) {
        if(isDisConnected){
            return;
        }
        if(AppManager.getAppManager().getActivityByName(CardInquireActivity.class)==null){
            return;
        }
        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(mContext);
        upDateDialog.setTitle(mContext.getResources().getString(R.string.simple_tips));
        upDateDialog.setMessage(content);
        upDateDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppManager.getAppManager().finishActivity();
            }
        });
        upDateDialog.setPositiveButton(getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (null != dev)
                    bthHelper.connect(dev);
                else
                {
                    scan();
                }
            }
        });
        upDateDialog.show();
    }



    /**
     * tab不在本页时断开连接
     */
   private boolean isDisConnected;
   public void diConnectDev(){
       isDisConnected = true;
       if(bthHelper==null){
           return;
       }
       if(bthHelper.isConnected()){
           bthHelper.disConnected();
       }
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        diConnectDev();
    }


    /**
     * 蓝牙连接回调
     */
    class AppIConnectCallBack implements IConnectCallBack {

        @Override
        public void gattConnect(final ScanDeviceBean scanDeviceBean) {
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    mBluetoothState.setText("请将IC卡保持贴在读卡器上");
                    mBluetoothInfo.setText("已连接：" + scanDeviceBean.getDevice().getName());
                    handler.sendEmptyMessage(READCARD);

                    closeLoading();

                }
            }, 1000);
        }


        @Override
        public void gattDisconnect(ScanDeviceBean scanDeviceBean) {
            closeLoading();
            mBluetoothState.setText(mContext.getResources().getString(R.string.bluetooth_reader_search));
            mBluetoothInfo.setText(mContext.getResources().getString(R.string.not_connected_to_the_Bluetooth_reader));
            //showTimeOutDialog(mContext.getResources().getString(R.string.bluetooth_disconnected_tips));
            showToast(getResources().getString(R.string.bluetooth_disconnected_tips));
        }

        @Override
        public void connectError(BTException e) {
            showToast(e.getMessage());
        }

        @Override
        public void devBondNone(ScanDeviceBean scanDeviceBean) {

        }

        @Override
        public void devBonding(ScanDeviceBean scanDeviceBean) {

        }

        @Override
        public void devBonded(ScanDeviceBean scanDeviceBean) {

        }
    }


    /**
     * 蓝牙搜索
     */
    class AppIScanBlueTooth implements IScanBlueTooth {
        @Override
        public void scanBlueTooth(List<ScanDeviceBean> list, ScanDeviceBean scanDeviceBean, BTException e) {
            if (null != e)
            {
                showToast(e.getMessage());
                return;
            }
            if(list.size()==1){
                blueToolthConnect(list.get(0));
                return;
            }
            if(devDialog==null) {
                devDialog = new DevDialog(mContext, list);
            }else{
                devDialog.setScanList(list);
            }
            devDialog.setOnItemClick(new DevDialog.OnItemClick() {
                @Override
                public void onItemCLick(ScanDeviceBean idev) {
                    blueToolthConnect(idev);
                }
            });
            devDialog.show();
        }

        @Override
        public void scanTimeCallBack(int i) {
            if(i==0){
                closeLoading();
                mBluetoothState.setText(mContext.getResources().getString(R.string.bluetooth_reader_search));
                mBluetoothInfo.setText(mContext.getResources().getString(R.string.not_connected_to_the_Bluetooth_reader));
                showTimeOutDialog(mContext.getResources().getString(R.string.search_bluetooth_time_out));
            }
        }
    }
}

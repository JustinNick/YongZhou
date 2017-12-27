package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.userview.DevDialog;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.activity.CardRecodeActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.cardinquire.ReadInquireFragment;
import com.citylink.tsm.blecitycard.BleCityCompatible;
import com.citylink.tsm.blecitycard.blecard.BleBaseCityCard;
import com.citylink.tsm.blecitycard.blecard.BleCardManager;
import com.huichenghe.bleControl.Ble.BluetoothLeService;
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
 * 蓝牙读卡器
 */
public class BlueToothRechargeActivity extends BaseAppActivity implements IScanBlueTooth, IConnectCallBack {


    private ImageView mIvMoveCard;
    private TextView mBluetoothInfo, mBluetoothState;
    private Handler myHandler = new Handler();
    private static final int READCARD = 0;
    private static final int READING_CARD = 1;
    private static final int DISCONNECT_CARD = 2;

    private BlueToothHelper bthHelper;
    private ScanDeviceBean dev;

    private ArrayList<TradeRecord> mChargeRecordList = null;//充值记录
    private ArrayList<TradeRecord> mLocalRecordList = null;//消费记录

    /**
     * 读卡状态标记，如果读到卡，则不再读卡
     */
    private boolean isReadCard = false;
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case READCARD:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!isReadCard) {
                            ReadCard();
                        }

                    }

                });

                if (!isReadCard) {
                    handler.sendEmptyMessageDelayed(READCARD, 2000);
                }
                    break;
                case READING_CARD:
                    showLoading(getResources().getString(R.string.reading_the_card));
                    mBluetoothState.setText(R.string.reading_the_card);
                    break;
                case DISCONNECT_CARD:
                    mBluetoothState.setText(R.string.the_card_has_left_please_repaste_the_card);
                    break;
            }

            return false;
        }
    });

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blue_tooth_recharge;
    }

    @Override
    protected void initView() {


        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        mIvMoveCard = (ImageView) findViewById(R.id.move_card_imageview);
        mBluetoothInfo = (TextView) findViewById(R.id.tv_bluetooth_info);
        mBluetoothState = (TextView) findViewById(R.id.tv_bluetooth_state);
        startCardAnimal();

        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHelper();
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


    /**
     * 初始化搜索蓝牙设置
     */
    public void initHelper() {
        // TODO Auto-generated method stub
//        if(!AppManager.getAppManager().isServiceWork(this,"com.nci.tkb.btjar.service.BluetoothLeService")){
//            Intent intent = new Intent(this,BluetoothLeService.class);
//            intent.setAction("android.intent.action.RESPOND_VIA_MESSAGE");
//            startService(intent);
//        }
//        if (bthHelper == null) {
//            bthHelper = BlueToothHelper.getInstrance().build(this, this, this);
//            //执行搜索
//        }

        bthHelper = BlueToothHelper.getInstrance().build(this, this, this);
        scan();
    }

    /**
     * 搜索蓝牙列表
     */
    public void scan() {

        if (bthHelper.isConnected()) {
            handler.sendEmptyMessageDelayed(READCARD, 2000);
            return;
        }
        showLoading(LoadString.BLUETOOTH_SEARCH);

        bthHelper.scan(20);
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
    private boolean isFist=true;
    public void ReadCard() {
        try {

            if (bthHelper != null) {

                if(!isFist){
                    //如果第一次读卡，则不提示用户卡片是否为贴卡状太
                    handler.sendEmptyMessage(DISCONNECT_CARD);
                }
                isFist = false;
                // 卡片复位
                String re = bthHelper.sendApdu("62", 4000, false);

                /**初始化 城市  永州  4250  第三方设备为 通卡宝
                 *执行这句初始化后， UI层读取卡片信息 如：卡号 余额 记录 接口与NFC读写卡片接口几乎一致
                 * 如： 以下  readCardInfo 方法
                 * */
                handler.sendEmptyMessage(READING_CARD);
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

        String[] arg0 = {BleBaseCityCard.CARD_BLANCE, BleBaseCityCard.CARD_NUM,
                BleBaseCityCard.CARD_LAST_RECODE};
        Bundle cardundle = getBleCardInfo(arg0);

        BleBaseCityCard bleBaseCityCard = getBleBaseCityCard();
        if (cardundle != null && bleBaseCityCard != null) {


            String preBalance = cardundle.getString(BleBaseCityCard.CARD_BLANCE);
            String cardNum = cardundle.getString(BleBaseCityCard.CARD_NUM);
            cardNum = ((BleCityCompatible) bleBaseCityCard).getCardNum(cardNum);
            String blance = ((BleCityCompatible) bleBaseCityCard).getCardBalance(preBalance);

            String cardEnabled = BleCardManager.execuBleCmd("", "00B0950801");//查询卡启用状态
            L.e("卡片启用状态：" + cardEnabled);
            if (TextUtils.isEmpty(cardEnabled) || !cardEnabled.equals("019000")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showAertDialog("此卡已经被停用或者读卡失败");

                    }
                });
                return;
            }
            if (!cardNum.substring(0, 4).equals(SPString.CITY_CODE)) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        showAertDialog("暂时不支持该城市卡片充值");
                    }
                });
                return;
            }
            closeLoading();

            Intent intent = new Intent(this, RechargeActivity.class);

            intent.putExtra("type", 1);
            intent.putExtra("cardNum", cardNum);
            intent.putExtra("balance", blance);
            startActivity(intent);
            AppManager.getAppManager().finishActivity(this);
        }
    }

    CustomerAlertDailog upDateDialog;

    private void showAertDialog(String str) {
        if (isDevdisConnected) {
            return;
        }

        if (upDateDialog != null && upDateDialog.isShowing()) {
            return;
        }

        upDateDialog = new CustomerAlertDailog(this);
        upDateDialog.setTitle(getResources().getString(R.string.simple_tips));
        upDateDialog.setMessage(str);
        upDateDialog.setPositiveButton(getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AppManager.getAppManager().finishActivity(BlueToothRechargeActivity.this);
            }
        });

        upDateDialog.show();

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
        if (devDialog == null) {
            devDialog = new DevDialog(BlueToothRechargeActivity.this, list);
        } else {
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
        if (i == 0) {
            closeLoading();
            if (dev == null) {
//                bthHelper.scan(20);
//                showLoading(LoadString.BLUETOOTH_SEARCH);
                showTimeOutDialog(getResources().getString(R.string.search_bluetooth_time_out));
            }
        }
    }

    @Override
    public void gattConnect(final ScanDeviceBean scanDeviceBean) {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mBluetoothState.setText(R.string.lease_stick_a_card_on_the_card_reader);
                mBluetoothInfo.setText(getResources().getString(R.string.connected_tips) + scanDeviceBean.getDevice().getName());
                handler.sendEmptyMessageDelayed(READCARD,2000);

                closeLoading();

            }
        }, 1000);
    }

    @Override
    public void gattDisconnect(ScanDeviceBean scanDeviceBean) {
        closeLoading();
        mBluetoothState.setText(getResources().getString(R.string.bluetooth_reader_search));
        mBluetoothInfo.setText(getResources().getString(R.string.card_reader_device_is_disconnected));
        if(dev!=null){
            showLoading(LoadString.BLUETOOTH_SEARCH);
            blueToolthConnect(dev);
        }

    }

    @Override
    public void connectError(BTException e) {

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
     *
     * @param content 提示信息
     */
    private void showTimeOutDialog(String content) {
        if (bthHelper != null) {
            if (bthHelper.isConnected()) {
                return;
            }
        }

        if (isDevdisConnected) {
            return;
        }
        if (AppManager.getAppManager().getActivityByName(BlueToothRechargeActivity.class) == null) {
            return;
        }
        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(this);
        upDateDialog.setTitle(getResources().getString(R.string.simple_tips));
        upDateDialog.setMessage(content);
        upDateDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppManager.getAppManager().finishActivity(BlueToothRechargeActivity.this);
            }
        });
        upDateDialog.setPositiveButton(getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (null != dev)
                    bthHelper.connect(dev);
                else {
                    bthHelper.stopScan();
                    scan();
                }
            }
        });
        upDateDialog.show();
    }


    /**
     * 断开连接
     */

    private boolean isDevdisConnected;

    public void diConnectDev() {
        dev = null;
        isDevdisConnected = true;
        isReadCard = true;
        if (bthHelper == null) {
            return;
        }
        if (bthHelper.isConnected()) {

            bthHelper.disConnected();

        }
        bthHelper.unbindService();
        bthHelper = null;
    }

    @Override
    public void onDestroy() {

        animationSet.setAnimationListener(null);
        animationSet = null;
        diConnectDev();
        super.onDestroy();
    }
}

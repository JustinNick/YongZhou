package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

//import com.citylink.tsm.blecitycard.BleCityCompatible;
//import com.citylink.tsm.blecitycard.bean.BleCardPOR;
//import com.citylink.tsm.blecitycard.blecard.BleBaseCityCard;
//import com.citylink.tsm.blecitycard.blecard.BleCardManager;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylinkdata.cardnfc.BaseCityCard;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.APDUBean;
import com.citylinkdata.yongzhou.bean.ReAPDUBean;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.APDUPresenter;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.utils.TimeUtil;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.nci.tkb.btjar.base.IConnectCallBack;
import com.nci.tkb.btjar.base.IScanBlueTooth;
import com.nci.tkb.btjar.bean.ScanDeviceBean;
import com.nci.tkb.btjar.exception.BTException;
import com.nci.tkb.btjar.helper.BlueToothHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//public class BlueToothRechargeResultActivity2 extends BaseAppActivity implements APDUPresenter.OnAPDUPresenter {
public class BlueToothRechargeResultActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_recharge_result2);
    }

//    private ImageView mIvMoveCard;
//    private TextView mBluetoothInfo, mBluetoothState;
//    private Handler myHandler = new Handler();
//    private static final int READCARD = 0;
//
//    private BlueToothHelper bthHelper;
//    private ScanDeviceBean dev;
//
//    private ArrayList<TradeRecord> mChargeRecordList = null;//充值记录
//    private ArrayList<TradeRecord> mLocalRecordList = null;//消费记录
//
//    private APDUPresenter apduPresenter;
//    private String amount, tradePwd, bankid, tn;
//    private String sequenceId = "";
//
//    String apduSum = "0";
//    String lastApduSW = "";
//    String lastData = "";
//    String lastApdu = "";
//
//    private boolean isReadCardInfo=true;
//
//    /**
//     * 读卡状态标记，如果读到卡，则不再读卡
//     */
//    private boolean isReadCard = false;
//    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            singleThreadExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    if (!isReadCard) {
//                        ReadCard();
//
//                    }
//
//                }
//
//            });
//
//            if (!isReadCard) {
//                handler.sendEmptyMessageDelayed(READCARD, 200);
//            }
//            return false;
//        }
//    });
//
//    @Override
//    protected BasePresenter getPresenter() {
//        return null;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_blue_tooth_recharge_result2;
//    }
//
//    @Override
//    protected void initView() {
//        amount = getIntent().getStringExtra("amount");
//        tradePwd = getIntent().getStringExtra("tradePwd");
//        bankid = getIntent().getStringExtra("bankid");
//        tn = getIntent().getStringExtra("tn");
//
//        mIvMoveCard = (ImageView) findViewById(R.id.move_card_imageview);
//        mBluetoothInfo = (TextView) findViewById(R.id.tv_bluetooth_info);
//        mBluetoothState = (TextView) findViewById(R.id.tv_bluetooth_state);
//
//        apduPresenter = new APDUPresenter();
//        apduPresenter.setOnAPDUPresenter(this);
//        startCardAnimal();
//        initHelper();
//    }
//
//    /**
//     * 开启充值卡移动动画
//     */
//    private AnimationSet animationSet;
//
//    private void startCardAnimal() {
//        animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.translate);
//
//        mIvMoveCard.startAnimation(animationSet);
//        animationSet.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mIvMoveCard.startAnimation(animationSet);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//    }
//
//
//    /**
//     * 初始化搜索蓝牙设置
//     */
//    public void initHelper() {
//        // TODO Auto-generated method stub
//        isDisConnected = false;
//        if (bthHelper == null) {
//            bthHelper = BlueToothHelper.getInstrance().build(this, new AppIScanBlueTooth(), new AppIConnectCallBack());
//            //执行搜索
//        }
//        scan();
//    }
//
//    /**
//     * 搜索蓝牙列表
//     */
//    public void scan() {
//
//        if (bthHelper.isConnected()) {
//            handler.sendEmptyMessageDelayed(READCARD, 500);
//            return;
//        }
//        showLoading(LoadString.BLUETOOTH_SEARCH);
//        bthHelper.scan(6);
//    }
//
//    /**
//     * 搜索到设备列表后连接第一个蓝牙
//     *
//     * @param dev
//     */
//    public void blueToolthConnect(ScanDeviceBean dev) {
//        if (null != dev && null != dev.getDevice() && null != dev.getDevice().getAddress()) {
//            this.dev = dev;
//            if (bthHelper.isConnected()) {
//                showToast("设备已经连接");
//                return;
//            }
//            bthHelper.stopScan();
//            bthHelper.connect(dev);
//        }
//    }
//
//    /**
//     * 蓝牙设备搜索到并已连接以后
//     * 执行下边这个方法 读卡片操作
//     */
//
//    public void ReadCard() {
//        try {
//
//            if (bthHelper != null) {
//                // 卡片复位
//                String re = bthHelper.sendApdu("62", 4000, false);
//                /**初始化 城市  永州  4250  第三方设备为 通卡宝
//                 *执行这句初始化后， UI层读取卡片信息 如：卡号 余额 记录 接口与NFC读写卡片接口几乎一致
//                 * 如： 以下  readCardInfo 方法
//                 * */
//
//                BleCardManager.initBlueHelper("通卡宝", bthHelper, SPString.CITY_CODE);
//                readCardInfo();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 读取卡片信息
//     * <p>
//     * 跟NFC读取卡片信息接口几乎一致
//     */
//    private String cardNum;
//    private String balance;
//    String lastRecord;
//
//    private void readCardInfo() {
//        isReadCard=true;
////        showLoading(getResources().getString(R.string.recharge_running));
//        String[] arg0 = {BleBaseCityCard.CARD_BLANCE, BleBaseCityCard.CARD_NUM,
//                BleBaseCityCard.CARD_LAST_RECODE};
//        Bundle cardundle = getBleCardInfo(arg0);
//
//        BleBaseCityCard bleBaseCityCard = getBleBaseCityCard();
//        if (cardundle != null && bleBaseCityCard != null) {
//
//            String preBalance = cardundle.getString(BleBaseCityCard.CARD_BLANCE);
//            String cardNum_ = cardundle.getString(BleBaseCityCard.CARD_NUM);
//            cardNum = ((BleCityCompatible) bleBaseCityCard).getCardNum(cardNum_);
//            balance = ((BleCityCompatible) bleBaseCityCard).getCardBalance(preBalance);
//            lastRecord = String.valueOf(cardundle.getParcelableArray(BaseCityCard.CARD_LAST_RECODE));
//
//            APDUBean apduBean = new APDUBean("1005", sequenceId, cardNum, amount, tradePwd, bankid, tn,
//                    balance, lastRecord, apduSum, lastApduSW, lastData,
//                    lastApdu, tradePwd);
//
//            apduPresenter.getAPDU(apduBean);
//        }
//    }
//
//    /**
//     * 获取蓝牙读卡器某城市
//     *
//     * @return
//     */
//    public BleBaseCityCard getBleBaseCityCard() {
//        BleBaseCityCard baseCity = null;
//        baseCity = BleCardManager.getBaseCityBleCard(); //IC卡
//        return baseCity;
//    }
//
//    /**
//     * 通过蓝牙读卡器获取卡上信息
//     *
//     * @param key
//     * @return
//     */
//    public Bundle getBleCardInfo(String[] key) {
//        BleBaseCityCard cityCard = getBleBaseCityCard();
//        if (cityCard == null)
//            return new Bundle();
//        else {
//            Message msg = cityCard.getCardInfo(key);
//            return msg.getData();
//        }
//    }
//
//    /**
//     * 请求成功
//     *
//     * @param reAPDUBean
//     */
//    ArrayList<String> apdulist = new ArrayList<>();
//
//    @Override
//    public void onReChargeSuccess(ReAPDUBean reAPDUBean) {
//        sequenceId = reAPDUBean.getSequenceId();
//
//        ArrayList<ReAPDUBean.ApduListBean> apdus = (ArrayList<ReAPDUBean.ApduListBean>) reAPDUBean.getApduList();
//
//        if (apdus.size() != 0) {
//            apdulist.clear();
//            apdulist.addAll(getApduList(apdus));
////            isRechargeCard = true;
//            rechargeCard();
//        } else {
//            closeLoading();
//            float currentBalance = Float.parseFloat(balance) + Float.parseFloat(amount);
//            Intent intent = new Intent(this, RechargeSuccessActivity.class);
//            intent.putExtra("issuccess", true).putExtra("type", 1);
//            intent.putExtra("cardnum", cardNum);
//            intent.putExtra("balance", balance);
//            intent.putExtra("balance_current", String.valueOf(currentBalance));
//            intent.putExtra("time", TimeUtil.getCurrentTime(TimeUtil.FORMAT_DATE_TIME));
//            startActivity(intent);
//        }
//    }
//
//    private void rechargeCard() {
//
//
//        BleCityCompatible bleCityCompatible = (BleCityCompatible) getBleBaseCityCard();
//
//
//        BleCardPOR cardPOR = bleCityCompatible.transceiveApduList(apdulist, 0, SPString.CURRENTAPPAID);
//
//        lastApduSW = cardPOR.getLastApduSW();
//        lastData = cardPOR.getLastData();
//        lastApdu = cardPOR.getLastApdu();
//        apduSum = cardPOR.getAPDUSum();
//
//        APDUBean apduBean = new APDUBean("1005", sequenceId, cardNum, amount, tradePwd, bankid, tn,
//                balance, lastRecord, apduSum, lastApduSW, lastData,
//                lastApdu, tradePwd
//        );
//
//        apduPresenter.getAPDU(apduBean);
//    }
//
//    private ArrayList<String> getApduList(ArrayList<ReAPDUBean.ApduListBean> apdulist) {
//        ArrayList<String> list = new ArrayList<>();
//        for (ReAPDUBean.ApduListBean apduBean : apdulist) {
//            list.add(apduBean.getApdu());
//        }
//        return list;
//    }
//
//
//    @Override
//    public void onReChargeFailed(String respmsg) {
//        closeLoading();
//        Intent intent = new Intent(this, RechargeSuccessActivity.class);
//        intent.putExtra("issuccess", false).putExtra("type", 1);
//        intent.putExtra("cardnum", cardNum);
//        intent.putExtra("balance", balance);
//        intent.putExtra("balance_current", balance);
//        intent.putExtra("time", TimeUtil.getCurrentTime(TimeUtil.FORMAT_DATE_TIME));
//        startActivityForResult(intent, 0);
//    }
//
//    /**
//     * 蓝牙搜索回调
//     */
//    class AppIScanBlueTooth implements IScanBlueTooth {
//
//        @Override
//        public void scanBlueTooth(List<ScanDeviceBean> list, ScanDeviceBean scanDeviceBean, BTException e) {
//            blueToolthConnect(scanDeviceBean);
//        }
//
//        @Override
//        public void scanTimeCallBack(int i) {
//            if (i == 0) {
//                closeLoading();
//                showTimeOutDialog(getResources().getString(R.string.search_bluetooth_time_out));
//            }
//        }
//    }
//
//    /**
//     * 蓝牙连接异常提示dialog  超时、断开连接
//     *
//     * @param content 提示信息
//     */
//    private void showTimeOutDialog(String content) {
//        if (isDisConnected) {
//            return;
//        }
//        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(this);
//        upDateDialog.setTitle(getResources().getString(R.string.simple_tips));
//        upDateDialog.setMessage(content);
//        upDateDialog.setNegativeButton(getResources().getString(R.string.cancel), null);
//        upDateDialog.setPositiveButton(getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                if (null != dev)
//                    bthHelper.connect(dev);
//                else {
//                    scan();
//                }
//            }
//        });
//        upDateDialog.show();
//    }
//
//    /**
//     * 蓝牙连接回调
//     */
//    class AppIConnectCallBack implements IConnectCallBack {
//
//        @Override
//        public void gattConnect(final ScanDeviceBean scanDeviceBean) {
//            myHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    mBluetoothState.setText("请将一卡通贴在读卡器上");
//                    mBluetoothInfo.setText("已连接：" + scanDeviceBean.getDevice().getName());
//                    handler.sendEmptyMessage(READCARD);
//
//                    closeLoading();
//
//                }
//            }, 1000);
//        }
//
//
//        @Override
//        public void gattDisconnect(ScanDeviceBean scanDeviceBean) {
//            closeLoading();
//            mBluetoothState.setText(getResources().getString(R.string.bluetooth_reader_search));
//            mBluetoothInfo.setText(getResources().getString(R.string.not_connected_to_the_Bluetooth_reader));
////            showTimeOutDialog(getResources().getString(R.string.bluetooth_disconnected_tips));
//            showToast(getResources().getString(R.string.bluetooth_disconnected_tips));
//
//        }
//
//        @Override
//        public void connectError(BTException e) {
//            showToast(e.getMessage());
//        }
//
//        @Override
//        public void devBondNone(ScanDeviceBean scanDeviceBean) {
//
//        }
//
//        @Override
//        public void devBonding(ScanDeviceBean scanDeviceBean) {
//
//        }
//
//        @Override
//        public void devBonded(ScanDeviceBean scanDeviceBean) {
//
//        }
//    }
//
//    /**
//     * tab不在本页时断开连接
//     */
//    private boolean isDisConnected;
//
//    public void diConnectDev() {
//        isDisConnected = true;
//        if (bthHelper == null) {
//            return;
//        }
//        if (bthHelper.isConnected()) {
//            bthHelper.disConnected();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        animationSet.setAnimationListener(null);
//        animationSet = null;
//        diConnectDev();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 0) {
//            sequenceId = "";
//
//            apduSum = "0";
//            lastApduSW = "";
//            lastData = "";
//            lastApdu = "";
//            isReadCard=false;
//            //readCardInfo();//重走读卡流程
//        }
//    }
}

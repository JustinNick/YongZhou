package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.APDUBean;
import com.citylinkdata.yongzhou.bean.ReAPDUBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.APDUPresenter;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.RechargePresenter;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.utils.SystemUtil;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.iview.IRechargeView;
import com.unionpay.UPPayAssistEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值
 */
public class RechargeActivity extends BaseAppActivity implements IRechargeView {
    private static final int TYPE_NFC = 0;
    private static final int TYPE_BLE = 1;
    private static final int TYPE_BUDENG = 2;
    private WebView webView;
    private TextView tvCardNum, tvBalance;
    private EditText etCardNum;
    private CheckBox cbBindCard;
    private int type;//0:nfc充值跳转，1：通宝卡跳转 2.补登充值
    private String cardNum;
    private APDUPresenter apduPresenter;
    private String balance;

    //银联支付
    private static final int PLUGIN_VALID = 0;
    private static final int PLUGIN_NOT_INSTALLED = -1;
    private static final int PLUGIN_NEED_UPGRADE = 2;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //充值金额大于1000dialog关闭
                    largeRechargeDialog.dismiss();
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
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        cardNum = getIntent().getStringExtra("cardNum");

        if (type == TYPE_BUDENG) {
            findViewById(R.id.ll_recharge).setVisibility(View.GONE);
            etCardNum = (EditText) findViewById(R.id.et_cardnum);
            cbBindCard = (CheckBox) findViewById(R.id.cb_bind_card);
            cbBindCard.setChecked(true);
            etCardNum.setText(cardNum);
            cbBindCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        SPUtils.setCache(RechargeActivity.this,SPString.CARD_NUM,etCardNum.getText().toString().trim());
                    }else{
                        SPUtils.setCache(RechargeActivity.this,SPString.CARD_NUM,"");
                    }
                }
            });

        } else {

            balance = getIntent().getStringExtra("balance");

            findViewById(R.id.ll_fill_up).setVisibility(View.GONE);

            tvCardNum = (TextView) findViewById(R.id.tv_card_num);
            tvBalance = (TextView) findViewById(R.id.tv_balance);

            tvCardNum.setText(getResources().getString(R.string.cardnum_title) + cardNum);
            tvBalance.setText(getResources().getString(R.string.card_balance_title) + balance);


        }

//        showLargeRechargeDialog();
        showToast(R.string.large_recharge_tips);
        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        if (type == TYPE_NFC) {
            titleView.setTitle(getResources().getString(R.string.nfc_recharge));
        } else if (type == TYPE_BLE) {
            titleView.setTitle(getResources().getString(R.string.bluetooth_card_reader_recharge));
        } else if (type == TYPE_BUDENG) {
            titleView.setTitle(getResources().getString(R.string.fill_up_the_recharge));
        }


        webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        webSettings.setJavaScriptEnabled(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new AndroidtoJs(), "android");
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl(getLoadUrl());


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                L.e("shouldOverrideUrlLoading" + url);
                if (url.contains(getLoadUrl())) {
                    webView.loadUrl(url);
                }
                //强制跳转微信支付
                if(url.contains("weixin://")){
                    if(!isAppAvilible(getContext(),"com.tencent.mm")){
                        showToast("您的手机未检测到微信客户端，请先安装微信客户端。");
                        return true;
                    }
                    Intent wxIntent = new Intent();
                    wxIntent.setAction(Intent.ACTION_VIEW);
                    wxIntent.setData(Uri.parse(url));
                    startActivity(wxIntent);
                    return true;
                }
                //强制跳转支付宝支付地址
                if(url.contains("alipays://")){
//                    if(!isAppAvilible(getContext(),"com.eg.android.AlipayGphone")){
//                        showToast("您的手机未检测到支付宝客户端，请先安装支付宝客户端。");
//                        return true;
//                    }
                    Intent alipayIntent = new Intent();
                    alipayIntent.setAction(Intent.ACTION_VIEW);
                    alipayIntent.setData(Uri.parse(url));
                    startActivity(alipayIntent);
                    return true;
                }
                return true;
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading("加载中。。。");
                L.e("onPageStarted" + url);
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                closeLoading();
                L.e("onPageFinished" + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //加载出现失败
                super.onReceivedError(view, errorCode, description, failingUrl);
                L.e("onReceivedError====" + errorCode);
            }

        });


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //加载过程回调，progress是接受到的数据的百分比
                L.e("progress====" + progress);
                if (progress == 10 && i == 0) {
                    webView.loadUrl(getLoadUrl());
                    i++;
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                showToast(message);
                return true;
            }
        });



    }
    //检测微信、支付宝客户端
    public static boolean isAppAvilible(Context context,String packName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packName)) {
                    return true;
                }
            }
        }
        return false;
    }
//    //检测支付宝客户端
//    public static boolean checkAliPayInstalled(Context context) {
//        Uri uri = Uri.parse("alipays://platformapi/startApp");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
//        return componentName != null;
//    }
    /**
     * 提示用户 卡片余额+充值金额不能大于1000
     * 考虑到用户操作体验，暂未使用
     */
    CustomerAlertDailog largeRechargeDialog;
    private void showLargeRechargeDialog() {


        if(largeRechargeDialog==null) {
            largeRechargeDialog = new CustomerAlertDailog(RechargeActivity.this);
            largeRechargeDialog.setTitle(getResources().getString(R.string.simple_tips));
            largeRechargeDialog.setMessage(getResources().getString(R.string.large_recharge_tips));
        }

        if(!largeRechargeDialog.isShowing()) {
            largeRechargeDialog.show();
        }
        handler.sendEmptyMessageDelayed(0,3000);


    }

    private int i = 0;
    /**
     * 获取跳转充值h5界面 加载地址
     * 业务类型区分（01：nfc卡；02：大卡；03：手环/全终端；04：蓝牙读卡器载体；05：补登；06：二维码；）
     */
    private String getLoadUrl() {
        String cardType="";
        if (type == TYPE_NFC) {
            cardType="01";
        }else if(type==TYPE_BLE){
            cardType="04";
        }else if(type==TYPE_BUDENG){
            cardType="05";
        }

        Map<String, String> par = new HashMap<>();

        par.put("username", SPString.APPID + SPUtils.getCache(this, SPString.USERNAME));
        par.put("password", SPUtils.getCache(this, SPString.PASSWORD).toUpperCase());
        par.put("appVersion",Conts.isTestRuntimes ? SPString.RECHARGE_ENVIRONMENT:"");
        par.put("appId", SPString.APPID);//永州
        par.put("alipayapp", "Y");
        par.put("type", "1");
        par.put("mobileNo", SPUtils.getCache(this, SPString.USERNAME));
        par.put("reqResource", "1");
        par.put("cardType",cardType);
        par.put("trMoney","");
        if(type==TYPE_BUDENG){
            par.put("cardno",getCardNum(cardNum));
        }else{
            par.put("cardno",cardNum);
        }

        if(type==TYPE_BUDENG) {
            par.put("sysVesion", SystemUtil.getSystemVersion());//系统版本号
            par.put("clientVesion", String.valueOf(SystemUtil.getVersionCode(this)));//客户端版本
            par.put("resolution", SystemUtil.getScreenDensity_ByResources(this));//设备分辨率
            par.put("deviceName", SystemUtil.getSystemVersion());//设备名称
        }
        par.put("version", SystemUtil.getSystemVersion());//系统版本

        String data = par.toString();
        String subData = data.substring(1, data.length() - 1).replace(", ", "&");
        return Conts.GET_RECHARGE_H5 + "?" + subData;
    }

    private String getCardNum(String cardNum) {
        String newCardNum = "";
        if(cardNum.length()<13){
            newCardNum = "4250300100"+cardNum.substring(5,11);
        }else {
            String subCardNum = cardNum.substring(0,7).toUpperCase();
            String replaceNum = "";
            switch (subCardNum){
                case "SF74604":
                    replaceNum = "4250310144";
                    break;
                case "W746007":
                    replaceNum = "4250340100";
                    break;
                case "SF74600":
                    replaceNum = "4250300190";
                    break;
                case "GX74600":
                    replaceNum = "4250300199";
                    break;
            }
            newCardNum = replaceNum+cardNum.substring(7,13);
        }

        return newCardNum;
    }

    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解

        /**
         * 返回App的主页面
         */
        @JavascriptInterface
        public void backToHome() {
            AppManager.getAppManager().finishActivity();
        }

        /**
         * 弹出消息提示，对用户操作进行反馈
         *
         * @param paramString 消息的具体内容
         */
        @JavascriptInterface
        public void msgbox(String paramString) {
            L.e("msgbox==" + paramString);
//            if (paramString.contains("Error: Java exception was raised during method invocation")) {
//                return;
//            }
           showToast(paramString);

        }


        /**
         * 跳转到银联的插件页面
         *
         * @param paramString 银联的订单流水号
         */
        @JavascriptInterface
        public void unionpay(String paramString) {
            L.e("unionpay====" + paramString);
            //开启银联支付
            if (TextUtils.isEmpty(paramString)) {
                showToast(R.string.unionpay_call_failed);
            }

//            int ret = UPPayAssistEx.startPay(RechargeActivity.this, null, null, paramString, "00");

//            UPPayAssistEx.startPayByJAR(RechargeActivity.this, PayActivity.class, null, null, paramString, "00");
            startUnionpay(paramString);


        }

        /**
         * 修改用户的交易密码
         */
        @JavascriptInterface
        public void changePwd() {
            UiHelp.jumpResetTradePassword(RechargeActivity.this, 0);
            L.e("changePwd====");
        }

        /**
         * 用户对在线账户进行充值结束后，调用该方法进行NFC充值或通卡宝充值。具体NFC充值结果都是App端进行判断
         *
         * @param amount   充值金额,
         * @param tradePwd 交易密码
         * @param bankid   银行编号
         * @param tn       订单流水号
         */
        @JavascriptInterface
        public void chargePro(String amount, String tradePwd, String bankid, String tn) {
            L.e("amount===" + amount + "   tradePwd====" + tradePwd + "      ======bankid" + bankid + "tn===" + tn);
            if(!TextUtils.isEmpty(balance)){
                float currentBalance = Float.parseFloat(balance.replace("元",""))*100+ Float.parseFloat(amount.replace("元",""))*100;
                if(currentBalance>1000){
                    showToast(R.string.large_recharge_tips);
                }
            }
            //Double fen= Double.valueOf(amount)*100;
            //String amountFen = fen.intValue()+"";

                recharge(amount, tradePwd, bankid, tn);

        }

        /**
         * 补登充值成功 h5 回调
         * @param result
         * @param amount
         * @param cardno
         * @param opertime
         */
        @JavascriptInterface
        public void chargeResult(String result, String amount, String cardno, String opertime) {
            L.e("result===" + result + "   amount====" + amount + "      ======cardno" + cardno + "opertime====" + opertime);
            Intent intent = new Intent(RechargeActivity.this,RechargeSuccessActivity.class);
            intent.putExtra("type",2);
            intent.putExtra("issuccess",result.equals("0")?true:false);
            intent.putExtra("balance",balance);
            intent.putExtra("amount",amount);
            intent.putExtra("cardnum",cardno);
            intent.putExtra("time",opertime);

            startActivity(intent);
            AppManager.getAppManager().finishActivity(RechargeActivity.this);
        }
    }



    /**
     * 调用银联支付
     * @param paramString
     */
    private void startUnionpay(String paramString) {
        // mMode参数解释：
        // 0 - 启动银联正式环境
        // 1 - 连接银联测试环境
        int ret = UPPayAssistEx.startPay(this, null, null, paramString, SPString.UNIONPAY);
        L.e("ret==="+ret);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            L.e(" plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            UPPayAssistEx.installUPPayPlugin(RechargeActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        L.e("" + ret);
    }



    /**
     * 跳转充值卡片界面
     * @param amount
     * @param tradePwd
     * @param bankid
     * @param tn
     */

    private void recharge(String amount, String tradePwd, String bankid, String tn) {

        Intent intent = new Intent();
        if (type == 0) {
            intent.setClass(RechargeActivity.this, NFCRechargeResultActivity.class);
        } else {
            intent.setClass(RechargeActivity.this, BlueToothRechargeResultActivity.class);
        }
        intent.putExtra("amount", amount);
        intent.putExtra("tradePwd", tradePwd);
        intent.putExtra("bankid", bankid);
        intent.putExtra("tn", tn);


        if (type == 0) {
            AppManager.getAppManager().finishActivity(NFCRechargeActivity.class);

        } else {
            AppManager.getAppManager().finishActivity(BlueToothRechargeActivity.class);

        }
        startActivity(intent);
        AppManager.getAppManager().finishActivity(RechargeActivity.this);
//        startActivity(new Intent(this,BlueToothRechargeResultActivity.class));
    }

    public static int modifyTrdPwdRequestCode = 111;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (requestCode == modifyTrdPwdRequestCode) {
            if (resultCode == modifyTrdPwdRequestCode) {
//                webView.loadUrl("javascript:setPayPwd("+0+");");
//                L.e("设置密码状态==0");
            } else {
                webView.loadUrl("javascript:setPayPwd(" + 1 + ");");
                L.e("设置密码状态==1");
            }
        }

        if (data == null) {
            return;
        }


        String msg = "";
        int flag = 0;
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {

            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            //....代码查看demo
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");

            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            msg = "支付成功！";
            flag = 1;
        } else if (str.equalsIgnoreCase("fail")) {
            flag = 0;
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            flag = 0;
            msg = "你已取消了本次订单的支付!";
        }
        showToast(msg);
        webView.loadUrl("javascript:unionpayreturn(" + flag + ");");

    }

    @Override
    protected void onDestroy() {

        webView.stopLoading();
        webView = null;

        super.onDestroy();
    }
}

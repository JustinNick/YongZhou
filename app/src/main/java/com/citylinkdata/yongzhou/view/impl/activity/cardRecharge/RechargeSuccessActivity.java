package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;

public class RechargeSuccessActivity extends BaseAppActivity {
    private static final int TYPE_NFC = 0;
    private static final int TYPE_BLE = 1;
    private static final int TYPE_BUDENG = 2;
    private int type;//0nfc充值跳转 1.通宝卡充值跳转  2.补登充值跳转
    private boolean isRechargeSuccess;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_success;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        isRechargeSuccess = intent.getBooleanExtra("issuccess", true);
        String cardNum = intent.getStringExtra("cardnum");
        String amount = intent.getStringExtra("amount");
        String balance = intent.getStringExtra("balance");
        String time = intent.getStringExtra("time");
        String balanceCurrent = intent.getStringExtra("balance_current");
        String errMsg = intent.getStringExtra("err_message");

        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onClick() {
                if (type == 0) {
                    AppManager.getAppManager().finishActivity(NFCRechargeResultActivity.class);
                } else if (type == 1) {
                    if (isRechargeSuccess == false) {
                        AppManager.getAppManager().finishActivity(BlueToothRechargeResultActivity.class);
                    }
                }
                AppManager.getAppManager().finishActivity(RechargeSuccessActivity.this);
            }
        });


        ImageView mIvState = (ImageView) findViewById(R.id.iv_recharge_state);
        TextView mtvState = (TextView) findViewById(R.id.tv_recharge_state);
        TextView mtvBalanceBefore = (TextView) findViewById(R.id.tv_balance_before);
        TextView mtvBalanceCurrent = (TextView) findViewById(R.id.tv_balance_current);
        TextView mtvCardNum = (TextView) findViewById(R.id.tv_card_num);
        TextView mtvRechargeTime = (TextView) findViewById(R.id.tv_recharge_time);
        TextView mtvTips = (TextView) findViewById(R.id.tv_tips);

        //温馨提示布局
        LinearLayout mllTips = (LinearLayout) findViewById(R.id.ll_tips);
        //充值失败选择继续充值或取消
        LinearLayout mllFailed = (LinearLayout) findViewById(R.id.ll_failed);

        //蓝牙nfc与补登充值分别设置不同的温馨提示
        if (type == TYPE_NFC | type == TYPE_BLE) {
            mtvTips.setText(R.string.recharge_tips_failed);
        } else if (type == TYPE_BUDENG) {
            mllFailed.setVisibility(View.GONE);
            mtvBalanceCurrent.setVisibility(View.GONE);
            mtvTips.setText(R.string.fill_up_tips_content);
        }


        if (isRechargeSuccess) {
            mIvState.setImageResource(R.drawable.icon_recharge_success);
            mtvState.setText(R.string.recharge_success);
//            mtvBalanceCurrent.setVisibility(View.VISIBLE);
            mllFailed.setVisibility(View.GONE);

            if (type == TYPE_NFC | type == TYPE_BLE) {
                //nfc和通卡宝充值成功不需要温馨提示
                mllTips.setVisibility(View.GONE);
            }

        } else {
            mIvState.setImageResource(R.drawable.icon_recharge_failed);
            mtvState.setText(R.string.recharge_failed);
            if (TextUtils.isEmpty(errMsg))
                mtvState.setText(getResources().getText(R.string.recharge_failed));
            else
                mtvState.setText(getResources().getText(R.string.recharge_failed) + "：" + errMsg);
            //充值失败不提示当前余额
            mtvBalanceCurrent.setVisibility(View.GONE);
        }


        mtvCardNum.setText(getResources().getString(R.string.recharge_card_number) + (TextUtils.isEmpty(cardNum) ? "" : cardNum));
        mtvRechargeTime.setText(getResources().getString(R.string.transaction_hour) + ((TextUtils.isEmpty(time)) ? "" : time));
        mtvBalanceCurrent.setText(getResources().getString(R.string.current_balance) + ((TextUtils.isEmpty(balanceCurrent)) ? "" : balanceCurrent));

        mtvBalanceBefore.setText(getResources().getString(R.string.recharge_amount) + ((TextUtils.isEmpty(amount)) ? "" : amount + "元"));
//        if (type == TYPE_BUDENG) {
//            //补登充值充前余额改成充值金额
//            mtvBalanceBefore.setText(getResources().getString(R.string.recharge_amount) + ((TextUtils.isEmpty(balance)) ? "" : balance));
//        } else {
//            mtvBalanceBefore.setText(getResources().getString(R.string.charge_the_balance_before) + ((TextUtils.isEmpty(balance)) ? "" : balance));
//        }

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue_recharge:
                setResult(1);
                AppManager.getAppManager().finishActivity(this);
                break;
            case R.id.btn_cancel:
                if (type == 0) {
//                    AppManager.getAppManager().finishActivity(NFCRechargeActivity.class);
                    setResult(2);

                    AppManager.getAppManager().finishActivity(NFCRechargeResultActivity.class);

                } else if (type == 1) {
                    AppManager.getAppManager().finishActivity(BlueToothRechargeResultActivity.class);
                }
                finish();
//                一期不做退款功能
//                UiHelp.jumpRefundsNoVertify(this);
                break;
        }
    }

}

package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.content.Intent;
import android.view.View;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.activity.BraceletRechargeActivity;

/**
 * Created by liqing on 2017/10/27.
 * 卡片充值
 */

public class CardRechargeActivity extends BaseAppActivity {

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_recharge;
    }

    @Override
    protected void initView() {

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.nfc_recharge:
                //NFC充值
                startActivity(new Intent(this,NFCRechargeActivity.class));
                break;
            case R.id.bluetooth_card_reader:
                //蓝牙读卡器

                startActivity(new Intent(this,BlueToothRechargeActivity.class));
                break;
            case R.id.fill_up_the_recharge:
                //补登充值
                startActivity(new Intent(this,FillUpActivity.class));
                break;
            case R.id.refund_application:
                //退款申请
                startActivity(new Intent(this,RefundsActivity.class));
                break;
            case R.id.wristband_card_reader:
                //手环充值
                startActivity(new Intent(this,BraceletRechargeActivity.class));
                break;
        }
    }
}

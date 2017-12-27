package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.citylink.tsm.blecitycard.bean.BleTradeRecord;
import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.BleTradeRecordAdapter;
import com.citylinkdata.yongzhou.adapter.TradeRecordAdapter;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.utils.AppManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 卡片余额与消费记录
 */
public class CardRecodeActivity extends BaseAppActivity {
    private ListView listView;
    private TextView mTvCardNum,mTvCardBalance,mNoRecordNotice,mTvCardExtendDate;

    private List<TradeRecord> logList = new ArrayList<>();
    private List<BleTradeRecord> bleLogList = new ArrayList<>();

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_recode;
    }

    @Override
    protected void initView() {
        hideActionBar();
        Intent intent = getIntent();
        String cardNum = intent.getStringExtra("Card_Num");
        String balance = intent.getStringExtra("Card_Balance");
        String extendDate=intent.getStringExtra("Card_ExtendDate");
        boolean isBle=intent.getBooleanExtra("IsBle",false);
        List<TradeRecord>  tradeRecords = intent.getParcelableArrayListExtra("Card_Logs");
       List<BleTradeRecord> bleTradeRecords= intent.getParcelableArrayListExtra("Card_Logs");

        mTvCardNum = (TextView) findViewById(R.id.tv_card_num);
        mTvCardBalance = (TextView) findViewById(R.id.tv_balance);
        mNoRecordNotice = (TextView) findViewById(R.id.tv_no_record);
        mTvCardExtendDate = (TextView)findViewById(R.id.tv_extend_date);
        mTvCardNum.setText("卡号：" + cardNum);
        mTvCardBalance.setText("余额：" + balance);
        mTvCardExtendDate.setText("有效期：" + extendDate);

        if(tradeRecords!=null||bleTradeRecords!=null) {
            if(isBle){
                bleLogList.addAll(bleTradeRecords);
            }else{
                logList.addAll(tradeRecords);
            }
            mNoRecordNotice.setVisibility(View.GONE);
        }else{
            mNoRecordNotice.setVisibility(View.VISIBLE);
        }

        listView = (ListView) findViewById(R.id.listview);
        if(logList!=null) {
            if(isBle){
                listView.setAdapter(new BleTradeRecordAdapter(this,bleLogList));
            }
            else {
                listView.setAdapter(new TradeRecordAdapter(this, logList));
            }

        }
    }

    public void onClick(View v){
        AppManager.getAppManager().finishActivity();
    }

}

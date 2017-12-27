package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.RefundsPresenter;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.activity.LoginActivity;

import java.util.List;

/**
 * Created by liqing on 2017/11/14.
 * 退款申请
 */

public class RefundsRecordAdapter extends BaseAdapter{
    private List<RehargeRecordBean.RecordeListBean> list;
    private Context context;
    private RefundsPresenter presenter;


    public RefundsRecordAdapter(Context context, List<RehargeRecordBean.RecordeListBean> list, RefundsPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate( R.layout.item_refunds_record,null);
        }
        TextView mTvCount = BaseViewHolder.get(convertView,R.id.tv_charge_count);
        TextView mTvCardNum = BaseViewHolder.get(convertView,R.id.tv_card_num);
        TextView mTvTradeTime = BaseViewHolder.get(convertView,R.id.tv_trade_time);
        TextView mTvTn = BaseViewHolder.get(convertView,R.id.tv_tn);
        TextView mTvState = BaseViewHolder.get(convertView,R.id.tv_state);

        final RehargeRecordBean.RecordeListBean record= list.get(position);
        mTvCount.setText(record.getChargeCount()+"元");
        mTvCardNum.setText(context.getResources().getString(R.string.item_ardnum_title)+record.getAppSerial());
        mTvTradeTime.setText(""+record.getTime());
        mTvTn.setText(context.getResources().getString(R.string.item_serial_number)+record.getSerial());
//        0 不可退款
//        1 可退款
//        2 退款中
//        3 已退款

        switch (record.getStatus()){
            case 0:
                mTvState.setText(record.getResult()+"");
                break;
            case 1:
                mTvState.setText("申请退款");
                break;
            case 2:
                mTvState.setText("退款中");
                break;
            case 3:
                mTvState.setText("已退款");
                break;
        }

        if(record.getStatus()==1){
            mTvState.setTextColor(context.getResources().getColor(R.color.blue));
        }else{
            mTvState.setTextColor(context.getResources().getColor(R.color.news_gray_dark));
        }

        mTvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record.getStatus()==1) {
                    showRequestRefundsDialog(record, position);
                }
            }
        });
        return convertView;
    }

    private void showRequestRefundsDialog(final RehargeRecordBean.RecordeListBean record,final int position) {

        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(context);
        upDateDialog.setTitle(context.getResources().getString(R.string.simple_tips));
        upDateDialog.setMessage(context.getResources().getString(R.string.determine_refunds));
        upDateDialog.setNegativeButton(context.getResources().getString(R.string.cancel), null);
        upDateDialog.setPositiveButton(context.getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

              presenter.requestRefund(record.getSerial(),record.getAppSerial(),record.getPayOrder(),record.getChargeCount()*100,position);
            }
        });
        upDateDialog.show();
    }
}

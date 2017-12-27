package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;

import java.util.List;

/**
 * Created by liqing on 2017/11/14.
 * 交易记录
 */

public class RecordAdapter extends BaseAdapter{
    private List<RehargeRecordBean.RecordeListBean> list;
    private Context context;


    public RecordAdapter(Context context, List<RehargeRecordBean.RecordeListBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate( R.layout.item_recharge_record,null);
        }
        TextView mTvCount = BaseViewHolder.get(convertView,R.id.tv_charge_count);
        TextView mTvCardNum = BaseViewHolder.get(convertView,R.id.tv_card_num);
        TextView mTvTradeTime = BaseViewHolder.get(convertView,R.id.tv_trade_time);
        TextView mTvTn = BaseViewHolder.get(convertView,R.id.tv_tn);
        TextView mTvState = BaseViewHolder.get(convertView,R.id.tv_state);
        ImageView mIvState = BaseViewHolder.get(convertView,R.id.iv_state);

        RehargeRecordBean.RecordeListBean record= list.get(position);
        mTvCount.setText(context.getResources().getString(R.string.recharge_amount)+record.getChargeCount()+"元");
        mTvCardNum.setText(context.getResources().getString(R.string.recharge_card_number)+record.getAppSerial());
        mTvTradeTime.setText(context.getResources().getString(R.string.transaction_hour)+record.getTime());
        mTvTn.setText(context.getResources().getString(R.string.serial_number)+record.getSerial());

        mTvState.setText(record.getResult());

        if(record.getResult().contains("成功")){
            Glide.with(context).load(R.drawable.record_recharge_success).into(mIvState);

        }else {
            Glide.with(context).load(R.drawable.record_recharge_failed).into(mIvState);
        }


        return convertView;
    }
}

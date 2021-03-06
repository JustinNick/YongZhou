package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.citylink.tsm.citycard.bean.TradeRecord;
import com.citylink.tsm.citycard.utils.FormatUtils;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;
import com.citylinkdata.yongzhou.view.impl.activity.CardRecodeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 2017/11/2.
 */

public class TradeRecordAdapter extends BaseAdapter {

    private Context context;
    private List<TradeRecord> list;

    public TradeRecordAdapter(Context context, List<TradeRecord> logList) {

        this.context = context;
        this.list = logList;
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
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_trade_record,null);
        }

        TextView mTvRecordType = BaseViewHolder.get(convertView,R.id.tv_record_type);
        TextView mTvTime = BaseViewHolder.get(convertView,R.id.tv_time);
        TextView mTvAmount = BaseViewHolder.get(convertView,R.id.tv_amount);

        mTvRecordType.setText(context.getResources().getString(R.string.card_recharge));
        mTvTime.setText(FormatUtils.formatDate(list.get(position).tradeDate)  + " " + FormatUtils.formatTime(list.get(position).tradeTime));
        mTvAmount.setText(FormatUtils.formatAmount(list.get(position).amount));
        return convertView;
    }
}

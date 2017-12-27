package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.OutletsBean;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;
import com.citylinkdata.yongzhou.view.impl.activity.OutletsActivity;

import java.util.List;

/**
 * Created by liqing on 2017/11/29.
 */

public class OutletsAdapter extends BaseAdapter{

    private Context context;
    private List<OutletsBean.Outlets> outletsList;


    public OutletsAdapter(Context context, List<OutletsBean.Outlets> outletsList) {
        this.context = context;
        this.outletsList = outletsList;
    }

    @Override
    public int getCount() {
        return outletsList.size();
    }

    @Override
    public Object getItem(int position) {
        return outletsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_outlets,null);
        }
        TextView mTvTitle = BaseViewHolder.get(convertView,R.id.tv_business_title);
        TextView mTvAddress = BaseViewHolder.get(convertView,R.id.tv_business_address);
        TextView mTvTime = BaseViewHolder.get(convertView,R.id.tv_business_time);
        TextView mTvCall = BaseViewHolder.get(convertView,R.id.tv_business_call);
        TextView mTvDetail = BaseViewHolder.get(convertView,R.id.tv_business_detail);

        OutletsBean.Outlets outlets = outletsList.get(position);
        mTvTitle.setText(outlets.getName());
        mTvAddress.setText(outlets.getName());
        mTvTime.setText(outlets.getName());
        mTvCall.setText(outlets.getName());
        mTvDetail.setText(outlets.getName());


        return convertView;
    }
}

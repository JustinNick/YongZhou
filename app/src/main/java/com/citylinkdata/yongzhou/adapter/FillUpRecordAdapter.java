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
import com.citylinkdata.yongzhou.bean.FillUpRecordBean;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;

import java.util.List;

/**
 * Created by liqing on 2017/11/14.
 * 交易记录
 */

public class FillUpRecordAdapter extends BaseAdapter{
    private List<FillUpRecordBean.BoardRecordListBean> list;
    private Context context;




    public FillUpRecordAdapter(Context context, List<FillUpRecordBean.BoardRecordListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate( R.layout.item_fillup_recharge_record,null);
        }
        TextView mTvCardNum = BaseViewHolder.get(convertView,R.id.tv_card_num);
        TextView mTvTradeTime = BaseViewHolder.get(convertView,R.id.tv_time);
        TextView mTvMoney = BaseViewHolder.get(convertView,R.id.tv_money);
        TextView mTvorder = BaseViewHolder.get(convertView,R.id.tv_order);
        TextView mTvRemark = BaseViewHolder.get(convertView,R.id.tv_remark);

        FillUpRecordBean.BoardRecordListBean boardRecordListBean = list.get(position);

        mTvCardNum.setText(context.getResources().getString(R.string.recharge_card_number)+boardRecordListBean.getCard_no());
        mTvTradeTime.setText(context.getResources().getString(R.string.transaction_hour)+boardRecordListBean.getOrder_time());
        mTvorder.setText(context.getResources().getString(R.string.order_title)+boardRecordListBean.getOrder_code());

//        0可退款
//        1 锁定
//        2 成功
//        3 失败
//        4 退款申请中
//        5 退款成功
        String status = "";
                switch (boardRecordListBean.getStatus()){
                    case 0:
                        status = "可退款";
                        break;
                    case 1:
                        status = "锁定";
                        break;
                    case 2:
                        status = "成功";
                        break;
                    case 3:
                        status = "失败";
                        break;
                    case 4:
                        status = "退款申请中";
                        break;
                    case 5:
                        status = "退款成功";
                        break;
                }


        mTvRemark.setText(context.getResources().getString(R.string.order_title)+boardRecordListBean.getOrder_code());
        mTvRemark.setText(context.getResources().getString(R.string.remark_title)+status);
        mTvMoney.setText(boardRecordListBean.getMoney()/100+"元");

//        TextView mTvCount = BaseViewHolder.get(convertView,R.id.tv_charge_count);
//        TextView mTvCardNum = BaseViewHolder.get(convertView,R.id.tv_card_num);
//        TextView mTvTradeTime = BaseViewHolder.get(convertView,R.id.tv_trade_time);
//        TextView mTvTn = BaseViewHolder.get(convertView,R.id.tv_tn);
//        TextView mTvState = BaseViewHolder.get(convertView,R.id.tv_state);
//        ImageView mIvState = BaseViewHolder.get(convertView,R.id.iv_state);
//
//        RehargeRecordBean.RecordeListBean record= list.get(position);
//        mTvCount.setText(context.getResources().getString(R.string.recharge_amount)+record.getChargeCount()+"元");
//        mTvCardNum.setText(context.getResources().getString(R.string.recharge_card_number)+record.getAppSerial());
//        mTvTradeTime.setText(context.getResources().getString(R.string.transaction_hour)+record.getTime());
//        mTvTn.setText(context.getResources().getString(R.string.serial_number)+record.getSerial());
//
//        mTvState.setText(record.getResult());
//
//        if(record.getResult().contains("成功")){
//            Glide.with(context).load(R.drawable.record_recharge_success).into(mIvState);
//
//        }else {
//            Glide.with(context).load(R.drawable.record_recharge_failed).into(mIvState);
//        }


        return convertView;
    }
}

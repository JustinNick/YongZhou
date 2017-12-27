package com.citylinkdata.bus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.citylinkdata.bus.R;

import java.util.List;

/**
 * Created by liqing on 2017/11/16.
 */

public class TipsAdapter extends BaseAdapter {

    private Context context;
    private List<Tip> tips;
    public TipsAdapter(Context mContext, List<Tip> tips) {
        this.context = mContext;
        this.tips = tips;
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        if(contentView == null){
            contentView = LayoutInflater.from(context).inflate(R.layout.bus_item,null);
        }
        Log.e("1","position=="+position);
        TextView textview = contentView.findViewById(R.id.textview);
        try {
            textview.setText(tips.get(position).getName());
        }catch (Exception e){
            Toast.makeText(context,"请求数据有误",Toast.LENGTH_SHORT).show();
        }
        return contentView;
    }
}

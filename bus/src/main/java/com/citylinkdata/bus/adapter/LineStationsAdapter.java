package com.citylinkdata.bus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citylinkdata.bus.R;

import java.util.ArrayList;

/**
 * Created by liqing on 2017/11/17.
 */

public class LineStationsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> stations;

    public LineStationsAdapter(Context context, ArrayList<String> stations) {
        this.stations = stations;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int i) {
        return stations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.bus_item_station,null);
        }
        TextView mTvStaion = view.findViewById(R.id.tv_staion);
        View viewTop = view.findViewById(R.id.view_space_top);
        View viewBottom = view.findViewById(R.id.view_space_bottom);


        LinearLayout linearLayout = view.findViewById(R.id.ll_line);
        if(i==0){
            viewTop.setVisibility(View.VISIBLE);
            viewBottom.setVisibility(View.GONE);
        }else if(i== stations.size()-1){
            viewTop.setVisibility(View.GONE);
            viewBottom.setVisibility(View.VISIBLE);
        }else {
            viewTop.setVisibility(View.GONE);
            viewBottom.setVisibility(View.GONE);
        }


        if(i==0){
            linearLayout.setVisibility(View.GONE);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
        }


        mTvStaion.setText(stations.get(i));
        return view;
    }
}

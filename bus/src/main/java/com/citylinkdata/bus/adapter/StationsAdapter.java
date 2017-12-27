package com.citylinkdata.bus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.busline.BusStationItem;
import com.citylinkdata.bus.R;

import java.util.List;

/**
 * Created by liqing on 2017/11/16.
 */

public class StationsAdapter extends BaseAdapter {

    private Context context;
    private List<BusStationItem> stations;
    public StationsAdapter(Context mContext, List<BusStationItem> stations) {
        this.context = mContext;
        this.stations = stations;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return stations.get(position);
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
        ImageView imageView = contentView.findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.station);
        textview.setText(stations.get(position).getBusStationName());
        return contentView;
    }
}

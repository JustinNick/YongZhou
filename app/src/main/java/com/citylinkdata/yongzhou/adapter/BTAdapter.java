package com.citylinkdata.yongzhou.adapter;

import android.bluetooth.le.ScanRecord;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.nci.tkb.btjar.bean.ScanDeviceBean;

import java.util.List;

/**
 * Created by dell on 2017/11/2.
 */

public class BTAdapter extends ArrayAdapter<ScanDeviceBean> {
    private List<ScanDeviceBean> devs;

    public BTAdapter(Context context, List<ScanDeviceBean> devs) {
        super(context, 0, devs);
        this.devs = devs;
        inflater = LayoutInflater.from(context);
        // TODO Auto-generated constructor stub
    }

    LayoutInflater inflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_bluetooth, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.mac = (TextView) convertView.findViewById(R.id.mac);
            // holder.posid =
            // (TextView)convertView.findViewById(R.id.posid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScanDeviceBean dev = getItem(position);
        if (null != dev.getDevice()) {
            holder.name.setText("Name:" + dev.getDevice().getName());
            holder.mac.setText("Mac:" + dev.getDevice().getAddress());
        }
        return convertView;
    }

    class ViewHolder {
        TextView name, mac;
    }

}

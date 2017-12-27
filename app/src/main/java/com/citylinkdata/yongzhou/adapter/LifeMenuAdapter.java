package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ItemMenuBean;
import com.nci.tkb.btjar.bean.ScanDeviceBean;

import java.util.List;

/**
 * Created by liqing on 2017/10/31.
 */

public class LifeMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemMenuBean> list;

    public LifeMenuAdapter(Context context, List<ItemMenuBean> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_menu_life,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
        final ItemMenuBean itemMenuBean = list.get(position);
        imageView.setImageResource(itemMenuBean.getImageId());
        return convertView;
    }
}

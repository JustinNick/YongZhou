package com.citylinkdata.yongzhou.userview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.nci.tkb.btjar.bean.ScanDeviceBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liqing
 * on 2016/11/11.
 * Email:liqingqq50@qq.com
 */

public class DevDialog extends Dialog {


    private Context context;
    List<ScanDeviceBean> list= new ArrayList<>();
    private DevAdapter devAdapter;

    private OnItemClick onItemClick;
    ListView listView;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public DevDialog(Context context, List<ScanDeviceBean> list) {
        super(context);
        this.context = context;
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog_devs);
        listView= (ListView) findViewById(R.id.listview);

//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);

        if(list!=null) {
            devAdapter = new DevAdapter();
            listView.setAdapter(devAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                onItemClick.onItemCLick(list.get(position));
            }
        });

    }



    @Override
    public void show() {
        super.show();

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    public void setScanList(List<ScanDeviceBean> scanList) {
        list.clear();
        if(scanList!=null){
            list.addAll(scanList);
        }
        if(devAdapter==null) {
            devAdapter = new DevAdapter();
            listView.setAdapter(devAdapter);

        }else{
            devAdapter.notifyDataSetChanged();
        }
    }


    class DevAdapter extends BaseAdapter{

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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_dev,null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textview);
            textView.setText(list.get(position).getDevice().getName());
            return convertView;
        }
    }

    public interface OnItemClick{
        void onItemCLick(ScanDeviceBean dev);
    }


}

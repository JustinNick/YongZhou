package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ItemMenuBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.view.impl.activity.BaseStandardWebActivity;

import java.util.List;

/**
 * Created by liqing on 2017/10/27.
 */

public class HomeMenuAdater extends BaseAdapter{
    private Context context;
    private List<ItemMenuBean> list;

    public HomeMenuAdater(Context context, List<ItemMenuBean> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_menu,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
        TextView textView = (TextView) convertView.findViewById(R.id.item_textview);
        final ItemMenuBean itemMenuBean = list.get(position);
        imageView.setImageResource(itemMenuBean.getImageId());
        textView.setText(itemMenuBean.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemMenuBean.getTitle().contains("客服")){
                    String tel = context.getResources().getString(R.string.tell_num);
                    Intent intent = new Intent(); intent.setAction(Intent.ACTION_CALL); intent.setData(Uri.parse(tel)); context.startActivity(intent);
                   return;
                }
                if(itemMenuBean.getTitle().contains("使用指南")){
                    Intent gIntent=new Intent(context,BaseStandardWebActivity.class);
                    gIntent.putExtra("title",itemMenuBean.getTitle());
                    gIntent.putExtra("url", Conts.APP_SYS_INFO +"?p=use_guide");
                    context.startActivity(gIntent);
                    return;
                }

                if(itemMenuBean.getTitle().contains("更多")){
                    Toast.makeText(context,context.getResources().getString(R.string.no_function),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(itemMenuBean.getActivity())){
                    Toast.makeText(context,context.getResources().getString(R.string.function_is_under_development),Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        Class c = Class.forName(itemMenuBean.getActivity());
                        if(c!=null) {

                            UiHelp.verifyLoginJump(context,c,null);
                        }
                    } catch (ClassNotFoundException e) {
                        Toast.makeText(context,context.getResources().getString(R.string.function_is_under_development),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return convertView;
    }
}

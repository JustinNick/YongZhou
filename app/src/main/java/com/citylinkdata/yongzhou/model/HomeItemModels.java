package com.citylinkdata.yongzhou.model;


import android.content.Context;
import android.content.res.TypedArray;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ItemMenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 2017/10/27.
 */

public class HomeItemModels {

    private Context context;

    public HomeItemModels(Context context) {
        this.context = context;
    }

    /**
     * 获取主界面第一部份menu信息
     * @return menu item集合
     */
    public List<ItemMenuBean> getHomeMenuItems(){
        List<ItemMenuBean> items = new ArrayList<>();
        String[] titles =context.getResources().getStringArray(R.array.home_menu_title);
        String[] activitys =context.getResources().getStringArray(R.array.home_menu_activity_name);

        TypedArray mTypedArray = context.getResources().obtainTypedArray(R.array.home_menu_image);
        for (int i = 0; i <mTypedArray.length(); i++) {
            ItemMenuBean itemMenuBean = new ItemMenuBean();
            itemMenuBean.setTitle(titles[i]);
            itemMenuBean.setImageId(mTypedArray.getResourceId(i, R.drawable.menu_inquire));
            itemMenuBean.setActivity(activitys[i]);
            items.add(itemMenuBean);
        }
        mTypedArray.recycle();

        return items;
    }

    /**
     * 获取主界面第二部份menu信息
     * @return menu item集合
     */
    public List<ItemMenuBean> getHomeOtherMenuItems(){

        List<ItemMenuBean> items = new ArrayList<>();
        String[] titles =context.getResources().getStringArray(R.array.home_other_menu_title);
        String[] activitys =context.getResources().getStringArray(R.array.home_other_menu_activity_name);
        TypedArray mTypedArray = context.getResources().obtainTypedArray(R.array.home_other_menu_image);
        for (int i = 0; i <mTypedArray.length(); i++) {
            ItemMenuBean itemMenuBean = new ItemMenuBean();
            itemMenuBean.setTitle(titles[i]);
            itemMenuBean.setImageId(mTypedArray.getResourceId(i, R.drawable.menu_inquire));
            itemMenuBean.setActivity(activitys[i]);
            items.add(itemMenuBean);
        }
        mTypedArray.recycle();

        return items;
    }


}

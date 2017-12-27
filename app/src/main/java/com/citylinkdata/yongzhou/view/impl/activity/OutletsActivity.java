package com.citylinkdata.yongzhou.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.OutletsAdapter;
import com.citylinkdata.yongzhou.bean.OutletsBean;
import com.citylinkdata.yongzhou.presenter.OutletsPresenter;
import com.citylinkdata.yongzhou.view.impl.iview.IOutletsView;

import java.util.ArrayList;
import java.util.List;

/**
 * 营业网点，现不用列表形式，直接用地图标注
 */
public class OutletsActivity extends BaseAppActivity<OutletsPresenter> implements IOutletsView,AdapterView.OnItemClickListener {

    private ListView listView;
    private OutletsAdapter outletsAdapter;
    List<OutletsBean.Outlets> outletsList = new ArrayList<>();

    @Override
    protected OutletsPresenter getPresenter() {
        return new OutletsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outlets;
    }

    @Override
    protected void initView() {

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        presenter.loadOutletsData();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadOutletsSuccess(List<OutletsBean.Outlets> outletsList) {
        outletsList.clear();
        outletsList.addAll(outletsList);
        initAdapter();

    }

    private void initAdapter() {
        if(outletsAdapter == null){
            outletsAdapter = new OutletsAdapter(this,outletsList);
        }
    }
}

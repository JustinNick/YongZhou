package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.RecordAdapter;
import com.citylinkdata.yongzhou.adapter.RefundsRecordAdapter;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.RefundsPresenter;
import com.citylinkdata.yongzhou.userview.DataErrorView;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.iview.IRefundsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 退款申请
 */
public class RefundsActivity extends BaseAppActivity<RefundsPresenter> implements IRefundsView {

    private ListView listView;
    private SmartRefreshLayout refreshlayout;
    private int page = 1;
    private List<RehargeRecordBean.RecordeListBean> list = new ArrayList<>();
    private RefundsRecordAdapter recordAdapter;
    private DataErrorView dataErrorView;

    @Override
    protected RefundsPresenter getPresenter() {
        return new RefundsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refunds;
    }

    @Override
    protected void initView() {
        //hideActionBar();
        listView = (ListView) findViewById(R.id.listview);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        dataErrorView = (DataErrorView) findViewById(R.id.data_erro_view);

        loadData();

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                loadData();

            }
        });
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                loadData();

            }
        });

        dataErrorView.setOnLoadClickListener(new DataErrorView.OnLoadClickListener() {
            @Override
            public void onLoadClick() {
                loadData();
            }
        });

        initAdapter();

    }


    /**
     * 加载数据成功
     *
     * @param rechareRecord
     */
    @Override
    public void onLoadSuccess(RehargeRecordBean rechareRecord) {
        if(rechareRecord.getRecodeList()==null){
            showToast(R.string.no_more_data);
            showErrorView();
            return;
        }
        if (rechareRecord.getRecodeList().size() == 0) {
            showToast(R.string.no_more_data);
            showErrorView();
            return;
        }
        if (page == 1) {
            list.clear();
        }
        list.addAll(rechareRecord.getRecodeList());

        initAdapter();

    }

    /**
     * 请求网络结束时
     */
    @Override
    public void onComplete() {
        if (page == 1) {
            refreshlayout.finishRefresh();
        } else {
            refreshlayout.finishLoadmore();
        }
        L.e("page" + page);
    }

    /**
     * 退款成功后刷新数据
     * @param position
     */
    @Override
    public void refresh(int position) {
        list.get(position).setStatus(2);
        recordAdapter.notifyDataSetChanged();
    }


    private void initAdapter() {
        showErrorView();
        if (recordAdapter == null) {
            recordAdapter = new RefundsRecordAdapter(this, list,presenter);
            listView.setAdapter(recordAdapter);
        } else {
            recordAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 没有数据时显示页面
     */
    private void showErrorView() {
        if (list.size() != 0) {
            dataErrorView.setVisibility(View.GONE);
        } else {
            dataErrorView.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        presenter.loadRecods(String.valueOf(page));
    }
}

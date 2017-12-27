package com.citylinkdata.yongzhou.view.impl.fragment.rechargerecord;

import android.view.View;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.FillUpRecordAdapter;
import com.citylinkdata.yongzhou.adapter.RecordAdapter;
import com.citylinkdata.yongzhou.bean.FillUpRecordBean;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.userview.DataErrorView;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqing on 2017/12/12.
 * 补登充值记录
 */

public class FillUpRecordFragment extends BaseFragment {
    private ListView listView;
    private SmartRefreshLayout refreshlayout;
    private int page = 1;
    private List<FillUpRecordBean.BoardRecordListBean> list = new ArrayList<>();
    private FillUpRecordAdapter recordAdapter;
    private DataErrorView dataErrorView;
    private HttpManager httpManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_record;
    }

    @Override
    protected void initView() {
        httpManager = new HttpManager(mContext);
        listView = (ListView) contentView.findViewById(R.id.listview);
        refreshlayout = (SmartRefreshLayout) contentView.findViewById(R.id.refreshlayout);
        dataErrorView = (DataErrorView) contentView.findViewById(R.id.data_erro_view);

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



    private void initAdapter() {
        showErrorView();
        if (recordAdapter == null) {
            recordAdapter = new FillUpRecordAdapter(mContext, list);
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
        Map<String, String> par = new HashMap<>();
        par.put("reqCode", "1045");
        par.put("appId", SPString.APPID);
        par.put("mobileNo", SPUtils.getCache(mContext, SPString.USERNAME));
        par.put("type", "");
        par.put("page", page+"");

        httpManager.asyncHttpGet(Conts.GET_FILL_UP_RECORD_LIST, par, new JsonCallBack() {

            @Override
            public void Success(String content) {
                L.e(content);
                FillUpRecordBean rechareRecord = new Gson().fromJson(content, FillUpRecordBean.class);
                if (rechareRecord.getBoardRecordList().size() == 0) {
                    showToast(R.string.no_more_data);
                    showErrorView();
                    return;
                }
                if (page == 1) {
                    list.clear();
                }
                list.addAll(rechareRecord.getBoardRecordList());

                initAdapter();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                showToast(errorMsg);
            }

            @Override
            public void onComplete() {
                if (page == 1) {
                    refreshlayout.finishRefresh();
                } else {
                    refreshlayout.finishLoadmore();
                }
                L.e("page" + page);
            }
        });
    }
}

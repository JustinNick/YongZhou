package com.citylinkdata.bus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.citylinkdata.bus.adapter.LinesAdapter;
import com.citylinkdata.bus.adapter.StationsAdapter;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 2017/11/16.
 */

public class OrdinaryFragment extends Fragment implements View.OnClickListener, TextWatcher, BusLineSearch.OnBusLineSearchListener, BusStationSearch.OnBusStationSearchListener {
    private View contentView;
    private Context mContext;

    private TextView tvNodata;
    private EditText mEtInput;
    private ListView mLvLines, mLvStations;
    private LinesAdapter linesAdapter;
    private StationsAdapter stationsAdapter;
    List<BusLineItem> lines = new ArrayList<>();
    List<BusStationItem> stations = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.bus_fragment_ordinary, null);

        if (getActivity() != null) {
            mContext = getActivity();
        }
        initView();
        return contentView;

    }

    private boolean isSearch = true;

    private void initView() {
        tvNodata = contentView.findViewById(R.id.tv_nodata);
        mEtInput = contentView.findViewById(R.id.et_input_search);

        mLvLines = contentView.findViewById(R.id.listview_lines);
        mLvStations = contentView.findViewById(R.id.listview_stations);


        mEtInput.addTextChangedListener(this);

        mLvLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BusLineItem busline = lines.get(i);
                if(busline.getBusStations().size()==0) {
                    searchBusById(busline.getBusLineId());
                    return;
                }else{
                    jumptoLineDetial(busline);
                }

            }
        });

        mLvStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isSearch = false;
                mEtInput.setText(stations.get(i).getBusStationName());
                mEtInput.setSelection(mEtInput.getText().toString().trim().length());
                lines.clear();
                lines.addAll(stations.get(i).getBusLineItems());
                initLinesAdapter();
            }
        });
    }

    private void searchBusById(String id) {
        BusLineQuery busLineQuery = new BusLineQuery(id, BusLineQuery.SearchType.BY_LINE_ID, CitycodeConfig.CITYCODE);
        BusLineSearch busLineSearch = new BusLineSearch(mContext, busLineQuery);
        busLineSearch.setOnBusLineSearchListener(new BusLineSearch.OnBusLineSearchListener() {
            @Override
            public void onBusLineSearched(BusLineResult busLineResult, int i) {
                jumptoLineDetial(busLineResult.getBusLines().get(0));
            }
        });
        busLineSearch.searchBusLineAsyn();
    }

    private void jumptoLineDetial(BusLineItem busline){
        Intent intent = new Intent(mContext, LineStationActivity.class);
        intent.putExtra("line_name", busline.getBusLineName());
        intent.putExtra("line_firsttime", busline.getFirstBusTime()==null?"暂无":new SimpleDateFormat("HH:mm").format(busline.getFirstBusTime()));
        intent.putExtra("line_lasttime", busline.getLastBusTime()==null?"暂无":new SimpleDateFormat("HH:mm").format(busline.getLastBusTime()));
        intent.putExtra("line_price", busline.getTotalPrice()==0?"暂无":String.valueOf(busline.getTotalPrice()));
        intent.putExtra("line_mileage", String.valueOf((float)(Math.round(busline.getDistance()*10))/10));
        intent.putStringArrayListExtra("stations", getLineStations(busline));
        startActivity(intent);
    }

    private ArrayList<String> getLineStations(BusLineItem busLineItem) {
        ArrayList<String> list = new ArrayList<>();
        List<BusStationItem> lineStaions = busLineItem.getBusStations();
        for (BusStationItem station : lineStaions) {
            list.add(station.getBusStationName());
        }
        return list;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!isSearch){
            isSearch=true;
            return;
        }
        searchBus(getSearchText());

    }

    /**
     * 搜索bus线路，并异步获取结果
     *
     * @param search 搜索关键字
     */
    private void searchBus(String search) {

        BusLineQuery busLineQuery = new BusLineQuery(search, BusLineQuery.SearchType.BY_LINE_NAME, CitycodeConfig.CITYCODE);
        BusLineSearch busLineSearch = new BusLineSearch(mContext, busLineQuery);
        busLineSearch.setOnBusLineSearchListener(this);
        busLineSearch.searchBusLineAsyn();
    }

    /**
     * 搜索bus站点，并异步获取结果
     *
     * @param search 搜索关键字
     */
    private void searchStation(String search) {


        BusStationQuery busStationQuery = new BusStationQuery(search, CitycodeConfig.CITYCODE);

        BusStationSearch busStationSearch = new BusStationSearch(mContext, busStationQuery);
        busStationSearch.setOnBusStationSearchListener(this);// 设置查询结果的监听

        busStationSearch.searchBusStationAsyn();
    }


    public String getSearchText() {
        return mEtInput.getText().toString().trim();
    }

    /**
     * 公交线路搜索回调监听
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onBusLineSearched(BusLineResult result, int rCode) {

        //如果result==null或者bus线路数为0，就进行站点抢险救灾
        if (result == null) {
            searchStation(getSearchText());
            return;
        }

        if (result.getBusLines() == null) {
            searchStation(getSearchText());
            return;
        }

        if (result.getBusLines().size() == 0) {
            searchStation(getSearchText());
        } else {
            lines.clear();
            lines.addAll(result.getBusLines());
            initLinesAdapter();

        }

    }


    /**
     * bus站点查询
     *
     * @param busStationResult
     * @param i
     */
    @Override
    public void onBusStationSearched(BusStationResult busStationResult, int i) {
        if (busStationResult != null) {
            if (busStationResult.getBusStations() != null) {
                stations.clear();
                stations.addAll(busStationResult.getBusStations());
                initStationsAdapter();
            }
        }
    }

    private void initLinesAdapter() {

        setVisibleListview(0);
        if (linesAdapter == null) {
            linesAdapter = new LinesAdapter(mContext, lines);
            mLvLines.setAdapter(linesAdapter);
        } else {
            linesAdapter.notifyDataSetChanged();
        }

    }

    private void initStationsAdapter() {
        setVisibleListview(1);
        if (stationsAdapter == null) {
            stationsAdapter = new StationsAdapter(mContext, stations);
            mLvStations.setAdapter(stationsAdapter);
        } else {
            stationsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 搜索状态：0:显示线路，1显示站点
     * @param type
     */
    private void setVisibleListview(int type) {
        if (type == 0) {
            if(lines.size()==0){
                tvNodata.setVisibility(View.VISIBLE);
            }else{
                tvNodata.setVisibility(View.GONE);
            }
            mLvLines.setVisibility(View.VISIBLE);
            mLvStations.setVisibility(View.GONE);
        } else {
            if(stations.size()==0){
                tvNodata.setVisibility(View.VISIBLE);
            }else{
                tvNodata.setVisibility(View.GONE);
            }
            mLvLines.setVisibility(View.GONE);
            mLvStations.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }
}

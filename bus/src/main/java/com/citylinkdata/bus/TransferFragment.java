package com.citylinkdata.bus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.citylinkdata.bus.adapter.BusResultListAdapter;
import com.citylinkdata.bus.util.SearchPointActivity;
import com.citylinkdata.bus.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by liqing on 2017/11/16.
 */

public class TransferFragment extends Fragment implements View.OnClickListener {

    private View contentView;
    private Context mContext;
    private ListView lvPaths;
    private EditText mEtStart, mEtEnd;
    private BusRouteResult mBusRouteResult;
    private Tip startTip, endTip;
    private TextView tvNodata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.bus_fragment_transfer, null);

        if (getActivity() != null) {
            mContext = getActivity();
        }
        initView();
        return contentView;

    }


    private void initView() {
        tvNodata = contentView.findViewById(R.id.tv_nodata);

        mEtStart = contentView.findViewById(R.id.et_input_start);
        mEtEnd = contentView.findViewById(R.id.et_input_end);

        lvPaths = contentView.findViewById(R.id.listview_path);
        contentView.findViewById(R.id.btn_search).setOnClickListener(this);
        mEtStart.setOnClickListener(this);
        mEtEnd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.et_input_start) {
            Intent intent = new Intent(mContext,SearchPointActivity.class);
            intent.putExtra("type", 0);
            intent.putExtra("searchtext",mEtStart.getText().toString().trim());

            startActivityForResult(intent,0);
        } else if (v.getId() == R.id.et_input_end) {
            Intent intent = new Intent(mContext,SearchPointActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("searchtext",mEtEnd.getText().toString().trim());

            startActivityForResult(intent, 1);
        } else if (v.getId() == R.id.btn_search) {
            search();
        }

    }

    private void search() {
        if (startTip == null) {
            Toast.makeText(mContext, "请输入起点", Toast.LENGTH_SHORT).show();
            return;
        }

        if (endTip == null) {
            Toast.makeText(mContext, "请输入终点", Toast.LENGTH_SHORT).show();
            return;
        }
        //搜索换乘方案
        RouteSearch routeSearch = new RouteSearch(mContext);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult result, int errorCode) {
                if (errorCode == 1000) {
                    if (result == null) {
                        setPathVisible(1);
                        return;
                    }
                    if (result.getPaths() == null) {
                        setPathVisible(1);
                        return;
                    }

                    mBusRouteResult = result;
                    BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(mContext, mBusRouteResult);
                    lvPaths.setAdapter(mBusResultListAdapter);

                    if (result.getPaths().size() == 0) {
                        setPathVisible(1);
                    } else {
                        setPathVisible(0);
                    }

                } else {
                    setPathVisible(1);
                    ToastUtil.showerror(mContext.getApplicationContext(), errorCode);
                }
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startTip.getPoint(), endTip.getPoint());
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BusLeaseWalk, "010", 0);
        routeSearch.calculateBusRouteAsyn(query);//开始规划路径

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            startTip = data.getParcelableExtra("tip");
            mEtStart.setText(startTip.getName());
        } else {
            endTip = data.getParcelableExtra("tip");
            mEtEnd.setText(endTip.getName());
        }
    }

    /**
     * 数据显示界面处理
     *
     * @param type 0有数据，1无数据
     */
    private void setPathVisible(int type) {

        if (type == 0) {
            tvNodata.setVisibility(View.GONE);
            lvPaths.setVisibility(View.VISIBLE);
        } else {
            tvNodata.setVisibility(View.VISIBLE);
            lvPaths.setVisibility(View.GONE);
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

package com.citylinkdata.bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.citylinkdata.bus.adapter.LineStationsAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class LineStationActivity extends AppCompatActivity {

    private ListView listView;
    private LineStationsAdapter lineStationsAdapter;
    private ArrayList<String> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_activity_line_station);

        String lineName = getIntent().getStringExtra("line_name");
        String lineFirstTime = getIntent().getStringExtra("line_firsttime");
        String lineLastTime= getIntent().getStringExtra("line_lasttime");
        String linePrice= getIntent().getStringExtra("line_price");
        String lineMileage= getIntent().getStringExtra("line_mileage");
        stations.addAll(getIntent().getStringArrayListExtra("stations")) ;

        TextView mTvLineName = (TextView) findViewById(R.id.tv_line_name);
        TextView mTvFirstTime = (TextView) findViewById(R.id.tv_first_time);
        TextView mTvLastTime = (TextView) findViewById(R.id.tv_last_time);
        TextView mTvLinePrice = (TextView) findViewById(R.id.tv_line_price);
        TextView mTvLineMileage = (TextView) findViewById(R.id.tv_line_mileage);

        View headView = LayoutInflater.from(this).inflate(R.layout.bus_headview,null);


        mTvLineName.setText(""+lineName);
        mTvFirstTime.setText(""+lineFirstTime);
        mTvLastTime.setText(""+lineLastTime);
        mTvLinePrice.setText("票价:"+linePrice);
        mTvLineMileage.setText("全程:"+lineMileage+"公里");

        listView = (ListView) findViewById(R.id.listview);

        initAdapter();

        listView.addHeaderView(headView);

    }

    private void initAdapter() {
        if(lineStationsAdapter==null){
            lineStationsAdapter = new LineStationsAdapter(this,stations);
            listView.setAdapter(lineStationsAdapter);
        }else{
            lineStationsAdapter.notify();
        }
    }

    public void onClick(View v){
        finish();
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getRunningActivityName());
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getRunningActivityName());
        MobclickAgent.onPause(this);
    }

    private String getRunningActivityName() {
        String contextString =this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }
}

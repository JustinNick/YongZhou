package com.citylinkdata.yongzhou.view.impl.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.OutletsBean;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.OutletsPresenter;
import com.citylinkdata.yongzhou.utils.GaoDeLocationUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IOutletsView;

import java.util.ArrayList;
import java.util.List;

public class OutletsMapActivity extends BaseAppActivity<OutletsPresenter> implements IOutletsView, AMap.OnMarkerClickListener, AMap.OnMapTouchListener {

    private MapView mapView;
    private AMap aMap;
    LatLng locationPosition;
    List<OutletsBean.Outlets> outletsList = new ArrayList<>();
    MyLocationStyle myLocationStyle;
    private LinearLayout tipsLinearLayout;
    private TextView tvTitle, tvDistance, tvAddress, tvTime, tvCall, tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// route_map

        tipsLinearLayout = (LinearLayout) findViewById(R.id.ll_tips);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvAddress = (TextView) findViewById(R.id.tv_business_address);
        tvTime = (TextView) findViewById(R.id.tv_business_time);
        tvCall = (TextView) findViewById(R.id.tv_business_call);
        tvDetail = (TextView) findViewById(R.id.tv_business_detail);

        aMap = mapView.getMap();
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapTouchListener(this);


        LatLng centerPoint = new LatLng(SPString.lat, SPString.lng);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerPoint,11,0,0)));

        presenter.loadOutletsData();

        GaoDeLocationUtils.intGps(this, new GaoDeLocationUtils.LocationCallBack() {
            @Override
            public void onSuccess(AMapLocation location) {
                locationPosition = new LatLng(location.getLatitude(), location.getLongitude());
            }
        });
//        //显示定位蓝点
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW) ;//定位一次。
    }

    @Override
    protected OutletsPresenter getPresenter() {
        return new OutletsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outlets_map;
    }

    @Override
    protected void initView() {

    }


    /**
     * mark 点击
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        showOutletsTips(marker.getPosition());
        return false;
    }


    /**
     * 查找单个marker对应的营业信息
     *
     * @param position 单个marker坐标
     */
    private void showOutletsTips(LatLng position) {

        float distance = AMapUtils.calculateLineDistance(locationPosition, position);
        for (int i = 0; i < outletsList.size(); i++) {
            OutletsBean.Outlets outlets = outletsList.get(i);
            if (outlets.getLat() == position.latitude && outlets.getLng() == position.longitude) {
                setTipsText(outlets, distance);

            }
        }
    }

    /**
     * 设置单个站点文字信息
     *
     * @param outlets  站点信息
     * @param distance
     */
    private void setTipsText(OutletsBean.Outlets outlets, float distance) {
        tipsLinearLayout.setVisibility(View.VISIBLE);
        tvTitle.setText(outlets.getName());
        tvDistance.setText("距离您" + String.valueOf((float) (Math.round(distance / 1000))) + "公里");
        tvAddress.setText("营业地址：" + outlets.getAdress());
        tvTime.setText("营业时间：" + outlets.getTime());
        tvCall.setText("营业电话：" + outlets.getTel());
        tvDetail.setText("业务功能：" + outlets.getBTypes());
    }

    @Override
    public void onLoadOutletsSuccess(List<OutletsBean.Outlets> outletss) {
        if (outletss == null) {
            return;
        }
        outletsList.clear();
        outletsList.addAll(outletss);
        if (outletsList.size() != 0) {
            for (OutletsBean.Outlets outlets : outletsList) {
                addMark(outlets);
            }
        }
    }


    /**
     * 添加标记点
     *
     * @param outlets
     */

    private void addMark(OutletsBean.Outlets outlets) {
        LatLng latLng = new LatLng(outlets.getLat(), outlets.getLng());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
//        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");

        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.icon_mark)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }



    @Override
    public void onTouch(MotionEvent motionEvent) {
        tipsLinearLayout.setVisibility(View.GONE);
    }
}

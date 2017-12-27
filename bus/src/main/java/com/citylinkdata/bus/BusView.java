package com.citylinkdata.bus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.amap.api.services.busline.BusStationItem;

import java.util.List;

/**
 * Created by liqing on 2017/11/16.
 */

public class BusView extends View {

    private List<BusStationItem> stations;
    private Paint mPaint;

    public List<BusStationItem> getStations() {
        return stations;
    }

    public void setStations(List<BusStationItem> stations) {
        this.stations = stations;
    }

    public BusView(Context context) {
        this(context, null);
    }

    public BusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int screenWidth = getWidth();
       for (int i=0;i<stations.size();i++){
           if(i==0){

           }
       }



    }
}

package com.citylinkdata.yongzhou.userview;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;


/**
 * Created by lym on 2016/12/20.
 */

public class GuideView extends LinearLayout {


    private Context mContext;
    private TextView[] tvImages = new TextView[4];
    private TextView[] tvTexts = new TextView[4];
    private View[] viewLines = new View[3];
    public GuideView(Context context) {
        super(context);
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    private void init(final Context context, AttributeSet attrs) {
        mContext = context;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.card_guid, this);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.guideView);
        int type = a.getInt(R.styleable.guideView_type,0);

        tvImages[0] = (TextView) findViewById(R.id.tv_guide_1);
        tvImages[1] = (TextView) findViewById(R.id.tv_guide_2);
        tvImages[2] = (TextView) findViewById(R.id.tv_guide_3);
        tvImages[3] = (TextView) findViewById(R.id.tv_guide_4);

        tvTexts[0] = (TextView) findViewById(R.id.tv_guide_text_1);
        tvTexts[1] = (TextView) findViewById(R.id.tv_guide_text_2);
        tvTexts[2] = (TextView) findViewById(R.id.tv_guide_text_3);
        tvTexts[3] = (TextView) findViewById(R.id.tv_guide_text_4);

        viewLines[0] = findViewById(R.id.view_line_1);
        viewLines[1] = findViewById(R.id.view_line_2);
        viewLines[2] = findViewById(R.id.view_line_3);

        for(int i=0;i<type;i++){
            tvImages[i].setBackgroundResource(R.drawable.guide_circle_hight);
            setTextColor(tvTexts[i],R.color.green_text);
            if(i>0){
                viewLines[i-1].setBackgroundColor(getResources().getColor(R.color.green_text));
            }
        }

    }


    private void setTextColor(TextView textView,int colorId){
        Resources resource = (Resources) ((Activity)mContext).getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(colorId);
        if (csl != null) {
            textView.setTextColor(csl);
        }
    }


}

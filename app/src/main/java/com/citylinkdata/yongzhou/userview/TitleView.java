package com.citylinkdata.yongzhou.userview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;


/**
 * Created by lym on 2016/12/20.
 */

public class TitleView extends LinearLayout implements View.OnClickListener {




    private Context mContext;
    private TextView  mTitle;
    protected TextView mRightTextView,mLeftTextView;
    protected ImageView mRightimageView,mLeftImageView;

    private LinearLayout mLeftLayout,mRightLayout;
    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    private void init(final Context context, AttributeSet attrs){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.title_toolbar,this);
        mTitle = (TextView) findViewById(R.id.title_textView);

        mRightTextView = (TextView) findViewById(R.id.right_textView);
        mRightimageView = (ImageView) findViewById(R.id.right_imageView);
        mRightLayout = (LinearLayout) findViewById(R.id.right_layout);


        mLeftLayout = (LinearLayout) findViewById(R.id.left_layout);
        mLeftTextView = (TextView) findViewById(R.id.left_textView);
        mLeftImageView = (ImageView) findViewById(R.id.left_imageView);



        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.titleBar);
        String title = a.getString(R.styleable.titleBar_title);
        String rightText = a.getString(R.styleable.titleBar_rightText);
        int imageReId = a.getResourceId(R.styleable.titleBar_rightImageReId,0);

        if(!TextUtils.isEmpty(title)){
            setTitle(title);
        }

        mRightLayout.setOnClickListener(this);
        mLeftLayout.setOnClickListener(this);



        if(TextUtils.isEmpty(rightText)){
            mRightTextView.setVisibility(VISIBLE);
            mRightTextView.setText(rightText);
        }else {
            mRightTextView.setVisibility(GONE);
        }

        if(imageReId!=0){
            mRightimageView.setVisibility(VISIBLE);
            mRightimageView.setImageResource(imageReId);
        }else {
            mRightimageView.setVisibility(GONE);
        }

        mRightLayout.setOnClickListener(this);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }


    public interface OnRightClickListener{
        void onClick();
    }


    public interface OnLeftClickListener{
        void onClick();
    }

    OnRightClickListener onRightClickListener;
    OnLeftClickListener onLeftClickListener;

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_layout:
                if(onLeftClickListener==null){
                    ((Activity)mContext).finish();

                }else{
                    onLeftClickListener.onClick();
                }
                break;

            case R.id.right_layout:
                if(onRightClickListener==null){
                    return;
                }
                onRightClickListener.onClick();
                break;
        }

    }

    public void setRightImageViewResouse(int imageId){
        mRightimageView.setImageResource(imageId);
    }

}

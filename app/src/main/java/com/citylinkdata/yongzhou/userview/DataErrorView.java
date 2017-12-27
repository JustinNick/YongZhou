package com.citylinkdata.yongzhou.userview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class DataErrorView extends LinearLayout implements View.OnClickListener {

    public interface OnLoadClickListener{
        void onLoadClick();
    }

    private OnLoadClickListener onLoadClickListener;

    public void setOnLoadClickListener(OnLoadClickListener onLoadClickListener) {
        this.onLoadClickListener = onLoadClickListener;
    }

    private Context mContext;
    TextView textView;

    private String erroText;

    public void setErroText(String erroText) {
        this.erroText = erroText;
        textView.setText(erroText+getResources().getString(R.string.load_again));
    }

    public DataErrorView(Context context) {
        super(context);
    }

    public DataErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    private void init(final Context context, AttributeSet attrs) {
        mContext = context;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.view_data_error_view, this);

        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(getResources().getString(R.string.no_data)+getResources().getString(R.string.load_again));

        imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onLoadClickListener == null){
            return;
        }
        onLoadClickListener.onLoadClick();
    }
}

package com.citylinkdata.yongzhou.userview;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;

import java.lang.ref.WeakReference;


/**
 * Created by liqing
 * on 2016/11/17
 * Email:liqingqq50@qq.com
 */

public class CustomerAlertDailog extends AlertDialog implements View.OnClickListener ,DialogInterface {
    private WeakReference<DialogInterface> mDialog;
    private View contentView;

    public CustomerAlertDailog(@NonNull Context context) {
        super(context);
        initViews(context);
    }



    private void initViews(Context context) {
        contentView = LayoutInflater.from(context).inflate(R.layout.dailog_notify,null);
        mNegativeButton = (TextView) contentView.findViewById(R.id.negative_button);
        mPositiveButton = (TextView) contentView.findViewById(R.id.positive_button);
        mTitleTextview = (TextView) contentView.findViewById(R.id.tv_title);
        mContentTextView = (TextView) contentView.findViewById(R.id.tv_content);
    }


    TextView mNegativeButton, mPositiveButton;
    TextView mTitleTextview, mContentTextView;

    OnClickListener negativeOnClickListener;
    OnClickListener positiveOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(contentView);

        mNegativeButton.setOnClickListener(this);
        mPositiveButton.setOnClickListener(this);
        mDialog = new WeakReference<DialogInterface>(this);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes(params);

    }

    public void setTitle(String title){
        if(TextUtils.isEmpty(title)){
            return;
        }
        mTitleTextview.setText(title);
    }

    public void setMessage(String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        mContentTextView.setText(msg);
    }

    public void setNegativeButton(String text, OnClickListener dialogOnClickListener){
        if(!TextUtils.isEmpty(text)){
            mNegativeButton.setVisibility(View.VISIBLE);
            mNegativeButton.setText(text);
        }
        if(dialogOnClickListener!=null) {
            negativeOnClickListener = dialogOnClickListener;
        }
    }

    public void setPositiveButton(String text, OnClickListener dialogOnClickListener){
        if(!TextUtils.isEmpty(text)) {
            mPositiveButton.setVisibility(View.VISIBLE);
            mPositiveButton.setText(text);
        }
        if(dialogOnClickListener!=null) {
            positiveOnClickListener = dialogOnClickListener;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.negative_button:
                dismiss();
                if(negativeOnClickListener==null){
                    return;
                }
                negativeOnClickListener.onClick(mDialog.get(), DialogInterface.BUTTON_NEGATIVE);
                break;
            case R.id.positive_button:
                dismiss();
                if(positiveOnClickListener==null){
                    return;
                }
                positiveOnClickListener.onClick(mDialog.get(), DialogInterface.BUTTON_POSITIVE);

                break;

        }
    }

//    public interface DialogInterface{
//        /**
//         * The identifier for the positive button.
//         */
//        public static final int BUTTON_POSITIVE = -1;
//
//        /**
//         * The identifier for the negative button.
//         */
//        public static final int BUTTON_NEGATIVE = -2;
//
//
//        interface OnClickListener {
//
//            public void onClick(android.content.DialogInterface dialog, int which);
//        }
//        void onClick(DialogInterface dialogInterface,int i);
//    }

}

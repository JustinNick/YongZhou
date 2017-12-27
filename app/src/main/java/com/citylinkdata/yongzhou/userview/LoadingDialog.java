package com.citylinkdata.yongzhou.userview;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;


/**
 * Created by liqing
 * on 2016/11/11.
 * Email:liqingqq50@qq.com
 */

public class LoadingDialog extends Dialog {

    private TextView mTvMessage;
    private ImageView mLoadingImageView;
    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        mLoadingImageView = (ImageView) findViewById(R.id.loadingImageView);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);


    }


    public void setMessage(CharSequence message) {
//      super.setMessage(message);
        if(mTvMessage!=null){
            if(message!=null){
                mTvMessage.setText(message);
            }
        }
    }

    @Override
    public void show() {
        super.show();
//        if (circle_anim != null) {
//            mLoadingImageView.startAnimation(circle_anim);  //开始动画
//        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

//        if (circle_anim != null) {
//
//            circle_anim.cancel();
//        }
    }

    /**
     * 开启旋转动画
     */
    Animation circle_anim;
    private void ShowAnimal(){
        circle_anim = AnimationUtils.loadAnimation(context, R.anim.loadroate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);

    }



}

package com.citylinkdata.yongzhou.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.BasePresenter;


/**
 * 手环充值
 */
public class BraceletRechargeActivity extends BaseAppActivity {
    private ImageView mIvMovePhone;
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bracelet_recharge;
    }

    @Override
    protected void initView() {
        mIvMovePhone = (ImageView) findViewById(R.id.move_phone_imageview);
        startCardAnimal();
    }

    /**
     * 开启充值卡移动动画
     */
    private AnimationSet animationSet;
    private void startCardAnimal() {
        animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.translate);

        mIvMovePhone.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvMovePhone.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationSet.setAnimationListener(null);
        animationSet = null;

    }
}

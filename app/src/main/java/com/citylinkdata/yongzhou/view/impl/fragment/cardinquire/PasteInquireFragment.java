package com.citylinkdata.yongzhou.view.impl.fragment.cardinquire;

import android.graphics.drawable.AnimationDrawable;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 贴卡查询
 */

public class PasteInquireFragment extends BaseFragment {

    private ImageView mIvNfcSinga,mIvMoveCard;
    private ImageView[] imageViews;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inquire_paste;
    }

    @Override
    protected void initView() {
        mIvNfcSinga = (ImageView) contentView.findViewById(R.id.nfc_signal_imageview);
        mIvMoveCard = (ImageView) contentView.findViewById(R.id.move_card_imageview);
        startCardAnimal();
        startNfcSingalAnimal();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 开启nfc搜索信号动画  帧动画
     */
    private void startNfcSingalAnimal() {
        AnimationDrawable nfcSingaAnimDrawble = (AnimationDrawable) mIvNfcSinga.getDrawable();
        nfcSingaAnimDrawble.start();
    }


    /**
     * 开启充值卡移动动画
     */
    private void startCardAnimal() {
        final AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.translate);

        mIvMoveCard.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvMoveCard.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }


}

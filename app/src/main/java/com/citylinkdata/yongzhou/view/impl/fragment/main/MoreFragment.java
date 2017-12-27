package com.citylinkdata.yongzhou.view.impl.fragment.main;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.view.impl.activity.AboutUsActivity;
import com.citylinkdata.yongzhou.view.impl.activity.BaseStandardWebActivity;
import com.citylinkdata.yongzhou.view.impl.activity.FeedbackActivity;
import com.citylinkdata.yongzhou.view.impl.activity.MainActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;

/**
 * Created by Liqing on 2017/10/26.
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected void initView() {
        contentView.findViewById(R.id.tv_feedback).setOnClickListener(this);
        contentView.findViewById(R.id.telephone_customer).setOnClickListener(this);
        contentView.findViewById(R.id.tv_about_us).setOnClickListener(this);
        contentView.findViewById(R.id.tv_user_guidance).setOnClickListener(this);
        contentView.findViewById(R.id.tv_update).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_feedback:
                UiHelp.verifyLoginJump(mContext, FeedbackActivity.class,null);
                break;
            case R.id.telephone_customer:
                ((MainActivity)mContext).requestPermission(new String[]{Manifest.permission.CALL_PHONE}, 0x0001);
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.tv_user_guidance:
                //startActivity(new Intent(mContext, UserGuidanceActivity.class));
                Intent guideInt=new Intent(mContext,BaseStandardWebActivity.class);
                guideInt.putExtra("title",getResources().getString(R.string.use_guidance));
                guideInt.putExtra("url", Conts.APP_SYS_INFO + "?p=use_guide");
                startActivity(guideInt);
                break;
            case R.id.tv_update:
                ((MainActivity)mContext).checkUpdate(1);
                break;
        }
    }
}

package com.citylinkdata.yongzhou.view.impl.fragment.cardinquire;

import android.view.View;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 绑卡查询
 */

public class BindInquireFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inquire_bind;
    }

    @Override
    protected void initView() {
        contentView.findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showToast(R.string.function_is_under_development);
    }
}

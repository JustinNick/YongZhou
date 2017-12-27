package com.citylinkdata.yongzhou.view.impl.iview;

import android.widget.ListView;

import com.citylinkdata.yongzhou.bean.OutletsBean;

import java.util.List;

/**
 * Created by liqing on 2017/11/30.
 */

public interface IOutletsView extends IBaseView {

    void onLoadOutletsSuccess(List<OutletsBean.Outlets> outletsList);
}

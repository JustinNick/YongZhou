package com.citylinkdata.yongzhou.view.impl.iview;

import com.citylinkdata.yongzhou.bean.RehargeRecordBean;

/**
 * Created by liqing on 2017/11/14.
 */

public interface IRechargeRecordView extends IBaseView{


    void onLoadSuccess(RehargeRecordBean rechareRecord);

    void onComplete();
}

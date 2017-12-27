package com.citylinkdata.yongzhou.view.impl.iview;

/**
 * Created by liqing on 2017/11/3.
 */

public interface IMainView extends IBaseView {
    void showUpdateDialog(String versionName, String description, String dowloadUrl,boolean mustUpdate);
}

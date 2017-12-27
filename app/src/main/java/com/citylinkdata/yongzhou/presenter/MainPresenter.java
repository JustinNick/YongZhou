package com.citylinkdata.yongzhou.presenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.VersionBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IMainView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/3.
 */

public class MainPresenter extends BasePresenter<IMainView> {


    /**
     * 检查版本
     * @param i  0 无需更新不需要提示 1无需更新需要提示
     */
    public void checkAppVersion(final int i) {
        Map<String,String> par = new HashMap<>();
        par.put("platform","Android");
        httpManager.asyncHttpPost(Conts.CHECK_VERSION, par, VersionBean.class, new ReqCallBack<VersionBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(VersionBean result) {
                VersionBean.Version version = result.getData();

                int code = Integer.parseInt(version.getVersion_code());
                boolean isMustUpdate=version.getUpgrade_method().equals("强制");
                if(code>getVersionCode()){
                    //弹出升级dailog

                    getView().showUpdateDialog(version.getVersion_name(),version.getDescription(),version.getUpgrade_url(),isMustUpdate);
                }else{
                    if(i==1){
                        getView().showToast(R.string.is_the_latest_version);
                    }
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }


    /**
     *  获取版本名称
     *  @return 当前应用的版本名称
     */
    public String getVersionName() {
        try {
            PackageManager manager = getView().getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getView().getContext().getPackageName(), 0);
            String version_name = info.versionName;
            int version_code = info.versionCode;
            return version_name;
        } catch (Exception e) {
            e.printStackTrace();
           return "";
        }
    }

    /**
     * 版本号
     * @return  当前应用的版本号
     */
    public int getVersionCode() {
        try {
            PackageManager manager = getView().getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getView().getContext().getPackageName(), 0);
            String version_name = info.versionName;
            int version_code = info.versionCode;
            return version_code;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}

package com.citylinkdata.yongzhou;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import com.citylink.tsm.blecitycard.blecard.BleCardManager;
import com.citylinkdata.cardnfc.NFCCardManager;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.utils.L;
import com.nci.tkb.btjar.utils.AppContextUtil;
import com.qibu.sdk.thirdpart.LoanThirdpart;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/**
 * Created by liqing on 2017/11/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;

    //友盟分享各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }


    /**
     * 上拉加载下拉刷新全局初始华
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white);//全局设置主题颜色
                return new BezierRadarHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);



        /**
         * ############### 360借贷宿主判断 ###################
         */
        //宿主可以配合自身app测试、线上环境的判断规则动态赋值。
        String env;
        env = SPString.ENV;
        LoanThirdpart.attachBaseContext(this, env);

        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        L.isDebug=true;
        AppContextUtil.init(this);

        //注册NFC卡
        NFCCardManager.registNFCCard(this);
        /**蓝牙读写卡片注册*/
        BleCardManager.registBleCardServer(this, null);

        //360借贷
        LoanThirdpart.onCreate();


        // 友盟
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5a017058f29d984ea80000a5", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "669c30a9584623e70e8cd01b0381dcb4");
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        //友盟分享
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        //友盟统计场景类型,
        // EScenarioType.E_UM_NORMAL 普通统计场景类型
        // EScenarioType.E_UM_GAME 游戏场景类型
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);

        MobclickAgent.openActivityDurationTrack(false);// 禁止默认的页面统计方式

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        LoanThirdpart.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        LoanThirdpart.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LoanThirdpart.onConfigurationChanged(newConfig);
    }

}

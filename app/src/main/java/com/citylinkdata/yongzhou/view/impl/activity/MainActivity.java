package com.citylinkdata.yongzhou.view.impl.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.presenter.MainPresenter;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.FileUtil;
import com.citylinkdata.yongzhou.view.impl.fragment.main.FindFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.main.HomeFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.main.MoreFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.main.MyFragment;
import com.citylinkdata.yongzhou.view.impl.iview.IMainView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主界面
 */
public class MainActivity extends BaseAppActivity<MainPresenter> implements IMainView {


    private Button[] mTabs;
    private Fragment[] mFragments;
    private Fragment mHomeFg, mFindFg, mMyFg, mMoreFg;
    private int index,currentTabIndex;
    private FragmentManager mFm;

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        isFragmentActivity=true;
        hideActionBar();
        checkUpdate(0);
//        applyYoumengPremission();

        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_home);
        mTabs[1] = (Button) findViewById(R.id.btn_find);
        mTabs[2] = (Button) findViewById(R.id.btn_my);
        mTabs[3] = (Button) findViewById(R.id.btn_more);
        mTabs[0].setSelected(true);

//        mHomeFg = new HomeFragment();
        mHomeFg = new HomeFragment();
        mFindFg = new FindFragment();
        mMyFg = new MyFragment();
        mMoreFg = new MoreFragment();
        mFragments = new Fragment[] { mHomeFg, mFindFg, mMyFg, mMoreFg };

        mFm = getSupportFragmentManager();
        mFm.beginTransaction().add(R.id.fragment_container, mHomeFg)
                .add(R.id.fragment_container, mFindFg).hide(mFindFg).show(mHomeFg).commit();
    }

    private void applyYoumengPremission() {

        if(Build.VERSION.SDK_INT>=23){
           //这里需要检测权限

            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};

            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }


    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                index = 0;
                break;
            case R.id.btn_find:
                index = 1;
                break;
            case R.id.btn_my:
                index = 2;
                break;
            case R.id.btn_more:
                index = 3;
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }


    public void checkUpdate(int type){
        presenter.checkAppVersion(type);
    }



    @Override
    public void showUpdateDialog(final String versionName, String description, final String dowloadUrl, final boolean isMustUpdate) {
        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(this);
        upDateDialog.setTitle(versionName+getResources().getString(R.string.version_updated_friends));
        upDateDialog.setMessage(description);
        upDateDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isMustUpdate){
                    AppManager.getAppManager().finishActivity();
                }
            }
        });
        upDateDialog.setPositiveButton(getResources().getString(R.string.update_immediately), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast(R.string.background_download);
                downLoadApk(versionName,dowloadUrl);
                if(isMustUpdate){
                    AppManager.getAppManager().finishActivity();
                }
            }
        });
        upDateDialog.show();
    }

    /**
     * 系统下载apk并安装
     * @param url
     */
    private void downLoadApk(String version,String url) {
        //取得系统的下载服务
        DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        //创建下载请求对象
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        final File file = FileUtil.getApkFile("yongzhou_"+ version +".apk");
        request.setDestinationUri(Uri.fromFile(file));
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"huanwei.apk");
        request.setNotificationVisibility(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        final long myDownloadReference = downloadManager.enqueue(request);


        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (myDownloadReference == reference) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    startActivity(i);
                    unregisterReceiver(this);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==3){
            ((MyFragment)mMyFg).refresh();
            return;
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }


    //=======================以下为友盟相关==============================================//
    public void share(){
        UMImage thumb =  new UMImage(this, R.mipmap.ic_launcher);
        UMWeb web = new UMWeb(Conts.APP_SHARE_LINK);
        web.setTitle("乐行.永州");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("永州市公交卡通NFC便捷充值应用工具。");//描述
        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.MORE)
                .setCallback(shareListener)
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"分享成功",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,"分享失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"分享取消",Toast.LENGTH_LONG).show();

        }
    };

    // //=======================以上为友盟相关==============================================//


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if(requestCode==0x0001){
            //申请到电话权限
            String tel = getResources().getString(R.string.tell_num);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse(tel));
            startActivity(intent);
        }
    }


    @Override
    public void finish() {
        //调用finish方法时算用户登录
        MobclickAgent.onProfileSignOff();
        super.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // 重置Fragment，防止当内存不足时导致Fragment重叠
        updateFragment(outState);
    }

    //    // 当activity非正常销毁时被调用
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        // 重置Fragment，防止当内存不足时导致Fragment重叠
//        updateFragment(outState);
//    }
//
    // 重置Fragment
    private void updateFragment(Bundle outState) {

//        mFm = getFragmentManager();
//        if(outState == null){
//
//            OneFragment oneFragment = new OneFragment();
//            mCurrentFragmen = oneFragment;
//            transaction.add(R.id.fl_show, oneFragment, mFragmentTagList[0]).commitAllowingStateLoss();
//            FragmentTransaction transaction = mFm.beginTransaction();
//            mHomeFg = new HomeFragment();
//            mFindFg = new FindFragment();
//            mMyFg = new MyFragment();
//            mMoreFg = new MoreFragment();
//            mFragments = new Fragment[] { mHomeFg, mFindFg, mMyFg, mMoreFg };
//
//            mFm = getSupportFragmentManager();
//            mFm.beginTransaction().add(R.id.fragment_container, mHomeFg)
//                    .add(R.id.fragment_container, mFindFg).hide(mFindFg).show(mHomeFg).commit();
//
//        }else{
//            // 通过tag找到fragment并重置
//            OneFragment oneFragment = (OneFragment) mFm.findFragmentByTag(mFragmentTagList[0]);
//            TwoFragment twoFragment = (TwoFragment) mFm.findFragmentByTag(mFragmentTagList[1]);
//            ThreeFragment threeFragment = (ThreeFragment) mFm.findFragmentByTag(mFragmentTagList[2]);
//            mFm.beginTransaction().show(oneFragment).hide(twoFragment).hide(threeFragment);
//        }
    }
}

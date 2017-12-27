package com.citylinkdata.yongzhou.view.impl.fragment.main;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.QueryBalanceBean;
import com.citylinkdata.yongzhou.bean.UserAvatorBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.userview.CustomerAlertDailog;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.activity.LoginActivity;
import com.citylinkdata.yongzhou.view.impl.activity.ModifyPasswordActivity;
import com.citylinkdata.yongzhou.view.impl.activity.PersonalInformationActivity;
import com.citylinkdata.yongzhou.view.impl.activity.TransactionPasswordActivity;
import com.citylinkdata.yongzhou.view.impl.activity.cardRecharge.RefundsActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import android.widget.PopupWindow.OnDismissListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 2017/10/26.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvUsername;
    private String username;
    private TextView mTvMoneyBalance,mTvIntegralBalance;
    private HttpManager mhttpManager;
    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;
    private WindowManager.LayoutParams params;
    private Window window;
    private PopupWindow mCameraDialog;
    private CircleImageView ivHead;
    @Override
    protected void initView() {

        ivHead = (CircleImageView) contentView.findViewById(R.id.iv_icon);

        contentView.findViewById(R.id.tv_modify_pwd).setOnClickListener(this);
        contentView.findViewById(R.id.tv_set_transaction_pwd).setOnClickListener(this);
        contentView.findViewById(R.id.tv_per_info).setOnClickListener(this);
        contentView.findViewById(R.id.tv_logout).setOnClickListener(this);
        contentView.findViewById(R.id.tv_refund_apply).setOnClickListener(this);
        mInflater =LayoutInflater.from(mContext);
//        mCameraDialog = new Dialog(mContext, R.style.Theme_AppCompat_Dialog);

        mTvUsername = (TextView) contentView.findViewById(R.id.tv_username);
        mTvMoneyBalance =(TextView) contentView.findViewById(R.id.money_balance);
        mTvMoneyBalance.setOnClickListener(this);
        mTvIntegralBalance =(TextView) contentView.findViewById(R.id.integral_balance);

        username = SPUtils.getCache(mContext, SPString.USERNAME);
        String iconUrl = SPUtils.getCache(mContext, SPString.ICON);
        if(!TextUtils.isEmpty(iconUrl)){
            Glide.with(mContext).load(iconUrl).into(ivHead);
        }
        mhttpManager=new HttpManager(mContext);

        if(TextUtils.isEmpty(username)){
            mTvUsername.setText(getResources().getString(R.string.not_loggin));
        }else{
            mTvUsername.setText(username);
            //查询账户余额信息
            queryAccountBalance();
        }

        ivHead.setOnClickListener(this);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_icon:
                if(TextUtils.isEmpty(SPUtils.getCache(mContext,SPString.USERNAME))) {
                    ((Activity)mContext).startActivityForResult(new Intent(mContext, LoginActivity.class).putExtra("type",1),0);
                }else{
                    //TODO:暂时不显示更换头像的功能,做了以后再打开
                    showChangeAvaterPopMenu();
                }
                break;
            case R.id.tv_refund_apply:
                UiHelp.verifyLoginJump(mContext,RefundsActivity.class,null);
                break;
            case R.id.tv_per_info:
                UiHelp.verifyLoginJump(mContext,PersonalInformationActivity.class,null);
                break;

            case R.id.tv_modify_pwd:
                UiHelp.verifyLoginJump(mContext,ModifyPasswordActivity.class,null);
                break;
            case R.id.tv_set_transaction_pwd:
                UiHelp.verifyLoginJump(mContext,TransactionPasswordActivity.class,null);
                break;
            case R.id.tv_username:
                UiHelp.verifyLoginJump(mContext,TransactionPasswordActivity.class,null);
                break;
            case R.id.tv_logout:
                String username = SPUtils.getCache(mContext,SPString.USERNAME);
                String password = SPUtils.getCache(mContext,SPString.PASSWORD);
                if(TextUtils.isEmpty(password)&&TextUtils.isEmpty(username)){
                    showToast(R.string.unlogin);
                }else{
                    showLogoutDialog();
                }
                break;
            case R.id.money_balance:
                queryAccountBalance();
                break;
            case R.id.tv_choose_photo:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                mCameraDialog.dismiss();
                break;
            case R.id.tv_take_photo:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                mCameraDialog.dismiss();
                break;
            case R.id.tv_cancel:
                mCameraDialog.dismiss();
                break;
        }
    }
    private void showChangeAvaterPopMenu(){
        LinearLayout contentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.bottom_pop_window, null);
        mCameraDialog = new PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        contentView.findViewById(R.id.tv_choose_photo).setOnClickListener(this);
        contentView.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(this);

        mCameraDialog.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mCameraDialog.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mCameraDialog.setOutsideTouchable(true);
        //设置可以点击
        mCameraDialog.setTouchable(true);
        //进入退出的动画
        mCameraDialog.setAnimationStyle(R.style.mypopwindow_anim_style);
        mCameraDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha( 1f,mContext);
            }
        });
        backgroundAlpha(0.4f,mContext);
        mCameraDialog.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public static void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * dailog提示用户是否退出登录
     */
    private void showLogoutDialog() {

        CustomerAlertDailog upDateDialog = new CustomerAlertDailog(mContext);
        upDateDialog.setTitle(getResources().getString(R.string.simple_tips));
        upDateDialog.setMessage(getResources().getString(R.string.logout_description));
        upDateDialog.setNegativeButton(getResources().getString(R.string.cancel), null);
        upDateDialog.setPositiveButton(getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SPUtils.setCacheClear(mContext);
                AppManager.getAppManager().finishAllActivity();
                MobclickAgent.onProfileSignOff();
                UiHelp.jumpLogin(mContext);
            }
        });
        upDateDialog.show();
    }

    /**
     * 请求查询余额
     */
    private void queryAccountBalance(){
        mTvMoneyBalance.setText("余额：查询中...") ;
        Map<String, String> par = new HashMap<>();
        par.put("mobileNo",SPUtils.getCache(mContext, SPString.USERNAME));
        mhttpManager.asyncHttpPost(Conts.QUERY_ACCOUNT_BALANDE, par, QueryBalanceBean.class, new ReqCallBack<QueryBalanceBean>() {
            @Override
            public void onComplete() {
                L.e("查询余额完成。");
            }

            @Override
            public void onReqSuccess(QueryBalanceBean result) {
                mTvMoneyBalance.setText("余额："+(result.getData().getBalance()/100d) +"元") ;
                mTvIntegralBalance.setText("积分：0");
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toast.makeText(mContext,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 跳转登陆页面后回来刷新此页面数据
     */
    public void refresh(){
        if(mTvUsername!=null) {
            mTvUsername.setText(SPUtils.getCache(mContext, SPString.USERNAME));
            //查询账户余额信息
            queryAccountBalance();
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {

                        setPicToView(head);// 保存在SD卡中
                        ivHead.setImageBitmap(head);// 用ImageView显示出来
                        /**
                         * 上传服务器代码
                         */
                        uploadIcon();
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    String fileName;
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 上传头像代码
     */
    private void uploadIcon() {
        Map<String,Object> par = new HashMap<String,Object>();
        if(fileName==null){
            return;
        }
        par.put("file",new File(fileName));
        par.put("MobileNo",username);
        showLoading(LoadString.UPLOADING);
        mhttpManager.upLoadFile(Conts.USERAVATOR,par,UserAvatorBean.class,new ReqCallBack<UserAvatorBean>(){

            @Override
            public void onComplete() {
                closeLoading();
            }

            @Override
            public void onReqSuccess(UserAvatorBean result) {
                SPUtils.setCache(mContext,SPString.ICON,result.getData().getPictureUrl());
                showToast(R.string.load_avator_success);
                if(!TextUtils.isEmpty(result.getData().getPictureUrl())){
                    Glide.with(mContext).load(result.getData().getPictureUrl()).into(ivHead);
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                showToast(errorMsg);
            }
        });
    }

}

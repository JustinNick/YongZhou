package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.CheckCardBean;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.FillUpPresenter;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;
import com.citylinkdata.yongzhou.view.impl.iview.IFillUpView;

/**
 * Created by Administrator on 2017/10/26 0026.
 * 补登充值
 */
public class FillUpActivity extends BaseAppActivity<FillUpPresenter> implements IFillUpView {

    private EditText etCardNum;

    @Override
    protected FillUpPresenter getPresenter() {
        return new FillUpPresenter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fill_up;
    }

    @Override
    protected void initView() {
        etCardNum = (EditText) findViewById(R.id.et_cardnum);
        etCardNum.setText(TextUtils.isEmpty(SPUtils.getCache(this, SPString.CARD_NUM))?"":SPUtils.getCache(this,SPString.CARD_NUM));
        etCardNum.setSelection(etCardNum.getText().toString().trim().length());
   }
    public void onClick(View v){
        if(TextUtils.isEmpty(etCardNum.getText().toString().trim())){
            showToast(R.string.please_input_cardnum);
            return;
        }
        //表面卡号可能是11位或13位,因为现在都经过服务器验证卡号，所以这句不验证了
//        if(!FormatJudge.isNumerCode(etCardNum.getText().toString().trim(),16)){
//            showToast(R.string.please_input_16_cardnum);
//            return;
//        }
        if(etCardNum.getText().toString().trim().length()!=11&&etCardNum.getText().toString().trim().length()!=13){
            showToast(R.string.card_num_erro);
            return;
        }

        // 将卡号发到服务器验证
        presenter.checkCard(getCardNum(etCardNum.getText().toString().trim()));



    }

    /**
     * 将卡面卡号转换成真实卡号
     * @param cardNum 卡面卡号
     * @return
     */
    private String getCardNum(String cardNum) {
        String newCardNum = "";
        if(cardNum.length()==11){
            newCardNum = "4250300100"+cardNum.substring(5,11);
        }else if(cardNum.length()==13){
            String subCardNum = cardNum.substring(0,7).toUpperCase();
            String replaceNum = "";
            switch (subCardNum){
                case "SF74604":
                    replaceNum = "4250310144";
                    break;
                case "W746007":
                    replaceNum = "4250340100";
                    break;
                case "SF74600":
                    replaceNum = "4250300190";
                    break;
                case "GX74600":
                    replaceNum = "4250300199";
                    break;
            }
            newCardNum = replaceNum+cardNum.substring(7,13);
        }

        return newCardNum;
    }



    @Override
    public void onSuccess(CheckCardBean result) {
        SPUtils.setCache(this,SPString.CARD_NUM,etCardNum.getText().toString().trim());
        Intent intent = new Intent(this,RechargeActivity.class);
        intent.putExtra("cardNum",etCardNum.getText().toString().trim());
        intent.putExtra("type",2);
        startActivity(intent);
        AppManager.getAppManager().finishActivity(this);
    }
}

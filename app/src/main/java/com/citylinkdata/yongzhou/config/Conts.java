package com.citylinkdata.yongzhou.config;

/**
 * Created by liqing on 2017/11/1.
 */

public class Conts {
    public static boolean isTestRuntimes = true;
    /**
     * APP应用后台地址
     **/
    private static String url = isTestRuntimes
            ? "http://122.224.226.42:9080"
            : "http://yzmanage.citylinkdata.com";

    /**
     * TSM数据后台地址
     **/
    private static String url_onecard = isTestRuntimes
            ? "http://ccltest.citylinkdata.com:8008/app/oneCard-mobile"
            : "https://app.citylinkdata.com/oneCard-mobile/";

//    public static String SEND_CODE = "http://172.16.2.33:8857/json/JSONServlet";

    /**
     * 注册
     */
    public static String REGIST = url + "/api/Mobile/RegisterUser";

    /**
     * 登录
     */
    public static String LOGIN = url + "/api/Mobile/RegisterUserLogin";
    /**
     * 用户更新资料
     */
    public static String UPDATE_PERSON_INFORMATION = url + "/api/RegisterUser/Update";
    /**
     * 修改密码
     */
    public static String MODIFY_PASSWORD = url + "/api/Mobile/changePwd";

    /**
     * 意见反溃
     */
    public static String FEEDBACK_ADD = url + "/api/Feedback/Add";
    /**
     * 获取数据字典接口
     */
    public static String GET_DATA_WORD = url + "/api/DataWord/GetDataWord";
    /**
     * 检测版本
     */
    public static final String CHECK_VERSION = url + "/api/Mobile/AppVersion";
    /**
     * 新闻列表
     */
    public static final String NEWS = url + "/api/News/GetTop";
    /**
     * 轮播图片列表
     */
    public static final String BANNERS = url + "/api/Banner/GetTop";
    /**
     * 新闻阅读
     */
    public static final String NEWS_DETAIL = url + "/App/News/Read/";
    /**
     * 发送短信
     */
    public static final String SEND_SMS = url + "/api/Mobile/SendSms";
    /**
     * 验证短信
     */
    public static final String CHECK_SMS = url + "/api/Mobile/VerifySms";
    /**
     * 重置登录密码、交易密码
     */
    public static final String RESET_PASSWORD = url + "/api/Mobile/ResetPwd";
    /**
     * 查询账户余额
     */
    public static final String QUERY_ACCOUNT_BALANDE = url + "/api/Mobile/QueryAccount";
    /**
     * 系统信息页面
     */
    public static final String APP_SYS_INFO = url + "/App/Info/Show";

    /**
     * 分享链接地址
     */
    public static final String APP_SHARE_LINK = url + "/App/Download";
    /**
     * 获取网点信息
     */
    public static final String GET_OUTLETS = url + "/api/BusinessStation/GetAll";
    /**
     * 上传头像接口
     */
    public static final String CHECK_CARD = url + "/Api/Mobile/CheckCardNumber/";

    /**
     *
     */
    public static final String USERAVATOR = url + "/Api/Upload/UserAvator/";

    /**
     * 公交站点线路查询
     */
    public static final String BUS_LINE_STATION_QUERY = "http://m.amap.com/navigation/bus/tab=naviby";
//    public static final String BUS_LINE_STATION_QUERY=url + "/App/Home/Index";


    /**
     * 获取写卡指令
     */
    public static final String APDU = url_onecard + "/appApdu.action";


    /**
     * 获取充值h5页面
     */
    public static final String GET_RECHARGE_H5 = url_onecard + "/logonApp.action";
    /**
     * 修改交易密码
     */
    public static final String MODIFY_TR_PWD = url_onecard + "/appResetPayPwdForAgent.action";

    /**
     * 获取交易记录
     */
    public static final String GET_CHARGE_RECORD_LIST = url_onecard + "/getChargeRecordList.action";
    /**
     * 获取交易记录
     */
    public static final String GET_FILL_UP_RECORD_LIST = url_onecard + "/user/appGetBoardRecordList.action";
//    public static final String GET_FILL_UP_RECORD_LIST = "http://ccltest.citylinkdata.com:8008/unionApp/taicang-0.0.1/user/appGetBoardRecordList.action";

    /**
     * 申请退款
     */
    public static final String GET_CHARGE_REFUNDS = url_onecard + "/appRefund.action";

    /**
     * 微商城网址
     */
    public static final String GET_MICRO_MALL_URL = "https://open.macaumarket.com/api/allpay/555555/pfConnect";

}

package com.citylinkdata.yongzhou.view.impl.fragment.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.HomeMenuAdater;
import com.citylinkdata.yongzhou.adapter.NewsAdapter;
import com.citylinkdata.yongzhou.bean.BannerBean;
import com.citylinkdata.yongzhou.bean.NewsBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.model.HomeItemModels;
import com.citylinkdata.yongzhou.userview.NoScrollGridView;
import com.citylinkdata.yongzhou.utils.DynamicTimeFormat;
import com.citylinkdata.yongzhou.utils.GlideImageLoader;
import com.citylinkdata.yongzhou.view.impl.activity.BaseStandardWebActivity;
import com.citylinkdata.yongzhou.view.impl.activity.MainActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by Liqing on 2017/10/26.
 */

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Integer[] images = {R.drawable.banner_more};//
    private List<Integer> list = Arrays.asList(images);
    private NoScrollGridView mMenuGridView, mOtherMenuGridView;
    private HomeMenuAdater mMenuAdapter, mOtherMenuAdapter;
    private ListView listView;
    private HttpManager mhttpManager;
    private  List<BannerBean.BannerDetailBean> banners = new ArrayList<>();
    private Banner banner;
    private int page=1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        View headview = LayoutInflater.from(mContext).inflate(R.layout.head_view_home,null);
        mhttpManager = new HttpManager(mContext);

        EditText etSearch = (EditText) headview.findViewById(R.id.et_search);
        etSearch.setOnClickListener(this);

        banner = (Banner) headview.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(banners.size()==0)
                    return;
                BannerBean.BannerDetailBean ban = banners.get(position);
                switch (ban.getEventType()){
                    case "url":
                        Intent intent = new Intent(mContext,BaseStandardWebActivity.class);
                        intent.putExtra("title",ban.getTitle());
                        intent.putExtra("url",ban.getEventExcutePath());
                        startActivity(intent);
                        break;
                    case "activity":

                        break;
                }

            }
        });
        //加载网络Banners
        loadBanners();
        //加载本地Banners
        banner.setImages(list);
        banner.setImages(list).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setImageLoader(new GlideImageLoader()).start();



        mMenuGridView = (NoScrollGridView) headview.findViewById(R.id.gv_menu);
        mOtherMenuGridView = (NoScrollGridView) headview.findViewById(R.id.gv_menu_other);

        HomeItemModels homeItemModels = new HomeItemModels(mContext);

        mMenuAdapter = new HomeMenuAdater(mContext, homeItemModels.getHomeMenuItems());
        mMenuGridView.setAdapter(mMenuAdapter);

//        mOtherMenuAdapter = new HomeMenuAdater(mContext,homeItemModels.getHomeOtherMenuItems());
//        mOtherMenuGridView.setAdapter(mOtherMenuAdapter);

        listView = (ListView) contentView.findViewById(R.id.listview);
        initAdapter();
        //加载新闻
        loadNews();

        listView.setOnItemClickListener(this);
        headview.findViewById(R.id.iv_share).setOnClickListener(this);

        listView.addHeaderView(headview);


        RefreshLayout refreshLayout = (RefreshLayout)contentView.findViewById(R.id.refreshlayout);
        ClassicsHeader mClassicsHeader = (ClassicsHeader)refreshLayout.getRefreshHeader();
        int deta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis()-deta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));

        mClassicsHeader.setSpinnerStyle(SpinnerStyle.FixedBehind);
        refreshLayout.setPrimaryColors(0xff444444, 0xffffffff);
                /*
                 * 由于是后面才设置，需要手动更改视图的位置
                 * 如果在 onCreate 或者 xml 中设置好[SpinnerStyle] 就不用手动调整位置了
                 */
        refreshLayout.getLayout().bringChildToFront(listView);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                loadNews();
                refreshlayout.finishRefresh(2000);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                loadNews();
                refreshlayout.finishLoadmore(2000);
            }
        });

    }


    /**
     * 加载新闻列表
     */
    private List<NewsBean.News> news = new ArrayList<>();

    private void loadNews() {
        Map<String, String> par = new HashMap<>();
        par.put("TakeCount", "4");
        par.put("CurrentPage", String.valueOf(page));
        mhttpManager.asyncHttpPost(Conts.NEWS, par, NewsBean.class, new ReqCallBack<NewsBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(NewsBean result) {
                if(result.getData()==null){
                    showToast(R.string.no_more_data);

                    return;
                }
                if (result.getData().size() == 0) {
                    showToast(R.string.no_more_data);
                    return;
                }
                if (page == 1) {
                    news.clear();
                }
                news.addAll(result.getData());

                initAdapter();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                showToast(errorMsg);
            }
        });
    }

    private void loadBanners(){
        Map<String, String> par = new HashMap<>();
        par.put("TakeCount", "5");
        mhttpManager.asyncHttpPost(Conts.BANNERS, par, BannerBean.class, new ReqCallBack<BannerBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(BannerBean result) {
                banners.addAll(result.getData());
                if(banners.size()>0){
                    //替换默认的Banner图像信息
                    List<String> urls = new ArrayList<String>();
                    for (BannerBean.BannerDetailBean ban:banners) {
                        urls.add(ban.getPictureUrl());
                    }
                    //banner.setImages(new ArrayList<String>());
                    banner.setImages(urls);
                    banner.start();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }
    private NewsAdapter newsAdapter;
    private void initAdapter() {
        if(newsAdapter==null){
            newsAdapter = new NewsAdapter(mContext,news);
            listView.setAdapter(newsAdapter);
        }else{
            newsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsBean.News mNew = news.get(position-1);
        Intent intent = new Intent(mContext, BaseStandardWebActivity.class);
        intent.putExtra("title",mContext.getResources().getString(R.string.news_title));
        intent.putExtra("url",Conts.NEWS_DETAIL + mNew.getID());
        mContext.startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_share:
                ((MainActivity)getActivity()).share();
                break;
            case R.id.et_search:
                UiHelp.jumpToBusSearch(mContext);
                break;
        }
    }
}

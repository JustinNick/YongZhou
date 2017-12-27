package com.citylinkdata.yongzhou.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.NewsBean;
import com.citylinkdata.yongzhou.utils.BaseViewHolder;
import com.citylinkdata.yongzhou.utils.L;

import java.util.List;

/**
 * Created by liqing on 2017/10/31.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsBean.News> news;

    public NewsAdapter(Context mContext, List<NewsBean.News> news) {
        context = mContext;
        this.news = news;
    }


    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news,null);
        }
        ImageView mImageView = BaseViewHolder.get(convertView,R.id.news_iamgeview);
        TextView mTvNewsTitle = BaseViewHolder.get(convertView,R.id.new_title);
        TextView mTvNewsFrom = BaseViewHolder.get(convertView,R.id.new_from);
        NewsBean.News mNew = news.get(position);
//        Glide.with(context).load(mNew.ge).into(mImageView);
        mTvNewsTitle.setText(mNew.getTitle());
        mTvNewsFrom.setText(mNew.getAuthor() + " | " + mNew.getCreatedTime().replaceAll("T"," "));
        String picUrl = mNew.getPicture();
        if(picUrl==null){
            Glide.with(context).load(R.drawable.news_nopicture).into(mImageView);
        } else{
            L.e("111","url22==="+picUrl);
            Glide.with(context).load(picUrl).into(mImageView);
        }
        return convertView;
    }
}

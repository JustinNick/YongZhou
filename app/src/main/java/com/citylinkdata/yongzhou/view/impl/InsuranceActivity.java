package com.citylinkdata.yongzhou.view.impl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ItemMenuBean;
import com.citylinkdata.yongzhou.config.UiHelp;

public class InsuranceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private int[] images = new int[]{R.drawable.cld_plane,R.drawable.cld_train};
    private String[] urls = new String[]{"http://pensionlife.95522.cn/tkylfcb/index.html#p1","http://pensionlife.95522.cn/tkylfcb/index.html#p2"};
    private String[] titles = new String[]{"飞常保","铁定保"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new InsuranceAdapter());

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UiHelp.jumpToStandardWeb(this,urls[position],titles[position],true);
    }

    class InsuranceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public Object getItem(int position) {
            return urls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(InsuranceActivity.this).inflate(R.layout.item_menu_life,null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.item_imageview);


            imageView.setImageResource(images[position]);

            return convertView;
        }
    }
}

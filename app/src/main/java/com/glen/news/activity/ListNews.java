package com.glen.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.glen.news.adapter.AdapterNews;
import com.glen.news.entity.CellDataNews;
import com.glen.news.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListNews extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener ,AdapterView.OnItemClickListener, View.OnClickListener
{
    private static final int REF_COMPLETE = 0x110;
    private SwipeRefreshLayout swipRef;
    private ListView lv;
    private ArrayList<CellDataNews> ll;
    private AdapterNews an;

    private Handler refhd = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REF_COMPLETE:
                    swipRef.setRefreshing(false);
                    Toast.makeText(ListNews.this, "刷新完成", Toast.LENGTH_SHORT).show();
                    ll.add(new CellDataNews("标题AAA","北京晚报","2016-12-01", R.drawable.dimg, "https://m.baidu.com/"));
                    ll.add(new CellDataNews("标题BBB","北京晚报", "2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
                    ll.add(new CellDataNews("标题CCC","北京晚报", "2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
                    an.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        init();
        initListview();
        runTask();

        test();
    }

    private void init(){
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    /**
     * 初始化listview
     */
    private void initListview(){
        ll = new ArrayList<>();
        an = new AdapterNews(ll , this);

        swipRef = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipRef.setOnRefreshListener(this);

        lv = (ListView) findViewById(R.id.NewsListView);
        lv.setAdapter(an);
        lv.setOnItemClickListener(this);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                String str = "onScroll -> view:"+absListView+" | i:"+i+" | i1:"+i1+" | i2:"+i2+" | count:"+ll.size();
                if((i+i1)==i2)
                {
//                    追加数据

                }
            }
        });
    }

    /**
     * test
     */
    private void test(){
        ll.add(new CellDataNews("标题","新华社","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题","新京报","2016-12-01",R.drawable.dimg, "http://m.360.com/"));
        ll.add(new CellDataNews("标题标题标题","今晚报","2016-12-01",R.drawable.dimg, "http://www.mi.com/"));
        ll.add(new CellDataNews("标题标题标题标题","羊城晚报","2016-12-01",R.drawable.dimg, "http://m.jd.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题","今日头条","2016-12-01",R.drawable.dimg, "https://m.taobao.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题","简书","2016-12-01",R.drawable.dimg, "http://m.hao123.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题","中央电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题","北京电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题","天津电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题","上海电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题标题","滨海早报","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题标题标题","北京晚报","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题标题标题标题","北京晚报","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题标题标题标题标题","北京晚报","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        ll.add(new CellDataNews("标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题","北京晚报","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));

        an.notifyDataSetChanged();
    }

    private void runTask(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.baidu.com");
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream inStream = conn.getInputStream();
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = inStream.read(buffer)) != -1)
                    {
                        outStream.write(buffer,0,len);
                    }
                    inStream.close();
                    byte[] data = outStream.toByteArray();
                    String str = new String(data , "utf-8");
                    System.out.println("result->"+str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra("url", ll.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onRefresh()
    {
        Toast.makeText(this, "开始刷新了", Toast.LENGTH_SHORT).show();
        refhd.sendEmptyMessageDelayed(REF_COMPLETE , 3000);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
package com.glen.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.glen.news.R;
import com.glen.news.activity.WebviewActivity;
import com.glen.news.adapter.AdapterNews;
import com.glen.news.entity.CellDataNews;
import com.glen.news.utils.Utils;
import com.glen.news.view.pulltorefresh.XListView;

import java.util.ArrayList;

/**
 * Created by baojiarui on 2016/12/5.
 *
 * 新闻列表fragment
 */
public class NewsListFragment extends Fragment implements XListView.IXListViewListener,AdapterView.OnItemClickListener{

    private View mView;
    protected XListView listview;
    private LinearLayout mErrorLayout, mLoadingLayout, mEmptyLayout;

    protected ArrayList<CellDataNews> datas;
    private AdapterNews mAdapter;

    protected int pageNo = 0;
    protected int pageSize = 10;

    private boolean isAllDataLoadCompleted = false;
    protected boolean isFirstVisible = true;

    public View getView() {
        return mView;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.from(getActivity()).inflate(R.layout.fragment_news_list, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEmptyLayout = (LinearLayout) mView.findViewById(R.id.empty_layout);
        mErrorLayout = (LinearLayout) mView.findViewById(R.id.error_layout);
        mErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();
                showLoadingLayout();
            }
        });
        mLoadingLayout = (LinearLayout) mView.findViewById(R.id.loading_layout);
        listview = (XListView) mView.findViewById(R.id.pull_refresh_list);
        listview.setPullRefreshEnable(true);
        listview.setPullLoadEnable(false);
        listview.setAutoLoadEnable(false);
        listview.setXListViewListener(this);
        listview.setRefreshTime(Utils.getTime());
        listview.setOnItemClickListener(this);
        /*if (getUserVisibleHint()) {
            isFirstVisible = false;
            requestData();
            if (pageNo == 0 && (datas == null || datas.size() == 0)) {
                showLoadingLayout();
            }
        }*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                requestData();
                if (pageNo == 0 && (datas == null || datas.size() == 0)) {

                }
            }else{
                showListView();
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        listview.setHintText(getResources().getString(R.string.footer_hint_load_normal));
        requestData();
    }

    @Override
    public void onLoadMore() {
        if (isAllDataLoadCompleted) {
            // 已经全部加载完毕
            listview.stopLoadMore();
            listview.setHintText(getResources().getString(R.string.footer_hint_load_complte));
            return;
        }
        requestData();
    }

    /**
     * 网络请求数据
     */
    public void requestData(){
        final ArrayList<CellDataNews> temps = new ArrayList<>();
        temps.add(new CellDataNews("洞庭湖网围打响“拆网之战”","新京报","2016-12-01",R.drawable.dimg, "http://www.bjnews.com.cn/news/2016/12/05/425964.html"));
        temps.add(new CellDataNews("全国铁路实行新列车运行图","新华社","2016-12-01",R.drawable.dimg, "http://3g.163.com/touch/article.html?channel=news&child=all&offset=1&docid=C7HJVFA70001875N"));
        temps.add(new CellDataNews("美元涨势正在枯竭","新京报","2016-12-01",R.drawable.dimg, "http://m.sohu.com/n/474915829/?wscrid=164_5"));
        temps.add(new CellDataNews("没有中国特朗普无法伟大","今晚报","2016-12-01",R.drawable.dimg, "http://m.sohu.com/n/474596059/?wscrid=37504_2"));
        temps.add(new CellDataNews("大学室友为份子钱险翻脸","羊城晚报","2016-12-01",R.drawable.dimg, "http://3g.163.com/touch/article.html?channel=news&child=all&offset=13&docid=C7HCRJQM0001899N"));
        temps.add(new CellDataNews("39批次手机上黑帮","今日头条","2016-12-01",R.drawable.dimg, "http://m.sohu.com/n/557064309/?wscrid=1140_6"));
        temps.add(new CellDataNews("楼市正进行一场大清洗","简书","2016-12-01",R.drawable.dimg, "http://m.sohu.com/n/557064305/?wscrid=1140_8"));
        temps.add(new CellDataNews("24小时国内要闻TOP10：深港通今日开通","新华社","2016-12-01",R.drawable.dimg, "http://news.xinhuanet.com/politics/2016-12/05/c_1120057127.htm"));
        temps.add(new CellDataNews("百度百度百度百度","中央电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));
        temps.add(new CellDataNews("标题标题标题标题标题标题标题标题","北京电视台","2016-12-01",R.drawable.dimg, "https://m.baidu.com/"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showData(temps);
            }
        }, 100);
    }

    private void showLoadingLayout() {
        if (datas == null || datas.size() == 0) {
            // 显示加载页面
            mLoadingLayout.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
        }
    }

    private void showErrorLayout() {
        if (datas == null || datas.size() == 0) {
            // 请求数据失败 或者没有数据返回
            mErrorLayout.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
            mLoadingLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
        }

    }

    private void showEmptyLayout(){
        if (datas == null || datas.size() == 0) {
            // 请求数据失败 或者没有数据返回
            mEmptyLayout.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            mLoadingLayout.setVisibility(View.GONE);
        }
    }

    private void showListView(){
        listview.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);
    }

    private void showData(ArrayList<CellDataNews> temps) {
        if (datas == null) {
            datas = new ArrayList<>();
            mAdapter = new AdapterNews(datas, getActivity());
            listview.setAdapter(mAdapter);
        }
        listview.stopLoadMore();
        listview.stopRefresh();
        listview.setRefreshTime(Utils.getTime());
        if (temps != null) {
            if (pageNo == 0) {    // 下拉刷新 或者 初次加载
                datas.clear();
            }
            datas.addAll(temps);

            showListView();
            if(pageNo >= 1){//test
                isAllDataLoadCompleted = true;
            }else{
                isAllDataLoadCompleted = false;
                pageNo++;
            }
            listview.setPullLoadEnable(true);

            mAdapter.notifyDataSetChanged();
        }else{
            if(pageNo >= 2){
                isAllDataLoadCompleted = true;
                return;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        intent.putExtra("url", datas.get(position-1).getUrl());
        startActivity(intent);
    }

}
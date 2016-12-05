package com.glen.news.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glen.news.R;
import com.glen.news.adapter.MyFragmentAdapter;
import com.glen.news.entity.Category;
import com.glen.news.fragment.NewsListFragment;

import java.util.ArrayList;

/**
 * Created by baojiarui on 2016/12/5.
 *
 * App首页
 */
public class NewsListActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private LinearLayout mLinearLayout;
    private HorizontalScrollView mHorizontalScrollView;
    private ArrayList<Category> categorys;
    private ViewPager mFragmentViewPager = null;

    private int currentFragmentIndex;
    private boolean isEnd;
    protected MyFragmentAdapter mFragmentPagerAdapter;
    protected ArrayList<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_news_list);

        test();

        initView();
        initMenu();
        initFragment();
    }

    /**
     * 测试数据
     */
    private void test(){
        mFragmentList = new ArrayList<Fragment>();
        categorys = new ArrayList<>();
        Category c0 = new Category();
        c0.setId(0);
        c0.setName("热点");
        Category c1 = new Category();
        c1.setId(1);
        c1.setName("要闻");
        Category c2 = new Category();
        c2.setId(2);
        c2.setName("娱乐");
        Category c3 = new Category();
        c3.setId(3);
        c3.setName("体育");
        categorys.add(0, c0);
        categorys.add(1, c1);
        categorys.add(2, c2);
        categorys.add(3, c3);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.btn_back){
            finish();
        }else if(view.getTag()!=null){
            mFragmentViewPager.setCurrentItem((Integer)view.getTag());
        }
    }

    protected void initView(){
        findViewById(R.id.btn_back).setVisibility(View.GONE);
        mFragmentViewPager = (ViewPager)findViewById(R.id.fragment_viewpager);
        mFragmentPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mFragmentViewPager.setOffscreenPageLimit(4);
        mFragmentViewPager.setAdapter(mFragmentPagerAdapter);
        mFragmentViewPager.setOnPageChangeListener(this);
    }

    private void initMenu(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.hsv_content);
        mLinearLayout.removeAllViews();
        int length = categorys.size();

        for (int i = 0 ; i < length ; i++) {
            RelativeLayout layout = new RelativeLayout(this);
            ImageView mImageView = new ImageView(this);
            TextView view = new TextView(this);
            view.setBackgroundColor(getResources().getColor(R.color.tran));
            view.setText(categorys.get(i).getName());
//            ToastFactory.showToast(this,getResources().getDimensionPixelOffset(R.dimen.font_24)+"");
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelOffset(R.dimen.font_15));
            view.setGravity(Gravity.CENTER);
            if(i==0){
                view.setTextColor(getResources().getColor(R.color.color_green));
            }else{
                view.setTextColor(getResources().getColor(R.color.color_txt_black));
            }
//            int itemWidth = (int) (view.getPaint().measureText(categorys.get(i).getName())+getResources().getDimension(R.dimen.x30));
            int itemWidth = (int) (view.getPaint().measureText(categorys.get(i).getName())+40);//garry
            RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(view,0, params);
            if(i==0){
                mImageView.setVisibility(View.VISIBLE);
            }else{
                mImageView.setVisibility(View.INVISIBLE);
            }
//            mLinearLayout.addView(layout, itemWidth, (int)getResources().getDimension(R.dimen.x105));
            mLinearLayout.addView(layout, itemWidth, 100);//garry
            layout.setOnClickListener(this);
            layout.setTag(i);
        }
    }

    private void initFragment() {
        mFragmentList.clear();
        int length = categorys.size();
        for(int i = 0 ; i < length ; i++){
            Bundle bundle = new Bundle();
//            bundle.putInt("",);
            Fragment fragment = new NewsListFragment();
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        mFragmentPagerAdapter.setFragments(mFragmentList);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            isEnd = false;
        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
            isEnd = true;
            if (mFragmentViewPager.getCurrentItem() == currentFragmentIndex) {
                mHorizontalScrollView.invalidate();
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int arg2) {
        if(!isEnd){
            mHorizontalScrollView.invalidate();
        }
    }


    @Override
    public void onPageSelected(int position) {
        RelativeLayout rel = (RelativeLayout)mLinearLayout.getChildAt(currentFragmentIndex);
        ((TextView)rel.getChildAt(0)).setTextColor(getResources().getColor(R.color.color_txt_black));
        currentFragmentIndex = position;
        RelativeLayout rel2 = (RelativeLayout)mLinearLayout.getChildAt(currentFragmentIndex);
        ((TextView)rel2.getChildAt(0)).setTextColor(getResources().getColor(R.color.color_green));
        int scrollX = 0;
        for(int i = 0 ;i < currentFragmentIndex;i++){
            scrollX += mLinearLayout.getChildAt(i).getWidth();
        }
        mHorizontalScrollView.smoothScrollTo(scrollX, 0);
    }
}

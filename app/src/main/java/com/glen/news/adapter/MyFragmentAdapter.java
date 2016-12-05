package com.glen.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baojiarui on 2016/12/5.
 *
 * 首页新闻类别适配器
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<String> titles;
	private final FragmentManager fm;
	public MyFragmentAdapter(FragmentManager fm) {
		super(fm);
        this.fm = fm;
	}

    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles){
        super(fm);
        this.fm = fm;
        this.fragments= fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titles == null){
            return super.getPageTitle(position);
        }else if(titles.size() >position){
            return titles.get(position);
        }else{
            return super.getPageTitle(position);
        }
    }

    public MyFragmentAdapter(FragmentManager fm,
                             ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    public void appendList(ArrayList<Fragment> fragment) {
        fragments.clear();
        if (!fragments.containsAll(fragment) && fragment.size() > 0) {
            fragments.addAll(fragment);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            List<Fragment> fs = fm.getFragments();
            if(fs!=null){
            	for (Fragment f : fs) {
            		ft.remove(f);
            		f = null;
            	}
            	ft.commit();
            	ft = null;
            	fs = null;
            	fm.executePendingTransactions();
            }
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (fragments.size() <= position) {
            position = position % fragments.size();
        }
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

}


package com.glen.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glen.news.entity.CellDataNews;
import com.glen.news.R;

import java.util.ArrayList;

public class AdapterNews extends BaseAdapter
{
    private ArrayList<CellDataNews> ll;
    private Context c;

    public AdapterNews(ArrayList<CellDataNews> ll, Context c)
    {
        this.ll = ll;
        this.c = c;
    }
    @Override
    public int getCount() {
        return ll.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view = LayoutInflater.from(c).inflate(R.layout.activity_cell_news, viewGroup, false);
        }
        TextView title = (TextView)view.findViewById(R.id.cellTitle);
        TextView author = (TextView)view.findViewById(R.id.cellAuthor);
        TextView date = (TextView)view.findViewById(R.id.cellDate);
        ImageView thumb =(ImageView)view.findViewById(R.id.cellImage);

        title.setText(ll.get(i).getTitle());
        author.setText(ll.get(i).getAuthor());
        date.setText(ll.get(i).getDate());
        thumb.setImageResource(ll.get(i).getThumb());

        return view;
    }
}

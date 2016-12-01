package com.glen.news.entity;

import java.io.Serializable;

/**
 * Created by glen on 2016/11/23.
 */

public class CellDataNews implements Serializable{
    private String title;
    private int thumb;
    private String author;
    private String date;
    private String url;

    public CellDataNews(String tit , String aut , String date , int thumb, String url)
    {
        this.title = tit;
        this.thumb = thumb;
        this.author = aut;
        this.date = date;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public int getThumb() {
        return thumb;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

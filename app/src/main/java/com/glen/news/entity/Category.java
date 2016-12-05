package com.glen.news.entity;

import java.io.Serializable;

/**
 * Created by baojiarui on 2016/12/5.
 *
 * 新闻类别
 */
public class Category implements Serializable{

	private long id;
	private String name;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

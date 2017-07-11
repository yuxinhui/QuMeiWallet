package com.gdyd.qmwallet.friends.bean;

import java.io.Serializable;
import java.util.List;

public class CircleBean implements Serializable {
	private static final long serialVersionUID = -8546131748993549867L;
	public String iconUri;// 头像uri
	public String nickName;// 昵称
	public int goodCount;// 赞数
	public int commentCount;// 评论数
	public String releaseTime;// 发布时间
	public String dynamicDesc;// 动态详情
	public boolean isClickGood;// 是否点过赞
	public List<String> imgList;// 图片集合
}

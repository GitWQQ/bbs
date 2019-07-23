package org.ssm.dufy.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class bbs implements Serializable {
	//xh，序号
	private String xh;
	//主题
	private String title;
	//内容
	private String content;
	//创建时间
	private String created;
	//有效性
	private String yxx;
	//作者id
	private String author_id;
	//图片地址url
	private String image_url;
	//是不是楼主
	private String is_parent;
	//
	private String parent_xh;
	//点赞数
	private Integer thumbs_up;
	//关于about_xh
	private String about_xh;
	//评论跟帖时间
	private String comment_time;
	
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	private User user;
	
	private About about;
	
	
	public About getAbout() {
		return about;
	}
	public void setAbout(About about) {
		this.about = about;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getYxx() {
		return yxx;
	}
	public void setYxx(String yxx) {
		this.yxx = yxx;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getIs_parent() {
		return is_parent;
	}
	public void setIs_parent(String is_parent) {
		this.is_parent = is_parent;
	}
	
	public Integer getThumbs_up() {
		return thumbs_up;
	}
	public void setThumbs_up(Integer thumbs_up) {
		this.thumbs_up = thumbs_up;
	}
	public String getAbout_xh() {
		return about_xh;
	}
	public void setAbout_xh(String about_xh) {
		this.about_xh = about_xh;
	}
	
	
	public String getParent_xh() {
		return parent_xh;
	}
	public void setParent_xh(String parent_xh) {
		this.parent_xh = parent_xh;
	}
	@Override
	public String toString() {
		return "bbs [xh=" + xh + ", title=" + title + ", content=" + content + ", created=" + created + ", yxx=" + yxx
				+ ", author_id=" + author_id + ", image_url=" + image_url + ", is_parent=" + is_parent + ", parent_xh="
				+ parent_xh + ", thumbs_up=" + thumbs_up + ", about_xh=" + about_xh + ", user=" + user + ", about="
				+ about + "]";
	}

}

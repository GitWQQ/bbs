package org.ssm.dufy.util.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.ssm.dufy.entity.About;
import org.ssm.dufy.entity.User;

public class Page {
	
	@Field
	private String xh;
	
	@Field
	private String title;
	
	@Field
	private String content;

	//创建时间
	@Field
	private String created;
	//有效性
	@Field
	private String yxx;
	//作者id
	@Field
	private String author_id;
	//图片地址url
	@Field
	private String image_url;
	//是不是楼主
	@Field
	private String is_parent;
	//
	@Field
	private String parent_xh;
	//点赞数
	@Field
	private Integer thumbs_up;
	//关于about_xh
	@Field
	private String about_xh;
	//评论跟帖时间
	@Field
	private String comment_time;
	
	@Field
	private String username;
	
	
	
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

	public String getParent_xh() {
		return parent_xh;
	}

	public void setParent_xh(String parent_xh) {
		this.parent_xh = parent_xh;
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

	public String getComment_time() {
		return comment_time;
	}

	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Page [xh=" + xh + ", title=" + title + ", content=" + content + ", created=" + created + ", yxx=" + yxx
				+ ", author_id=" + author_id + ", image_url=" + image_url + ", is_parent=" + is_parent + ", parent_xh="
				+ parent_xh + ", thumbs_up=" + thumbs_up + ", about_xh=" + about_xh + ", comment_time=" + comment_time
				+ ", username=" + username + "]";
	}

	
}

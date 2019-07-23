package org.ssm.dufy.entity;

import java.io.Serializable;

public class About implements Serializable {
	
	//序号
	private String xh;
	//积分
	private Integer jf;
	//星级，
	private String start;
	//贴子数量
	private Integer published_num;

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public Integer getJf() {
		return jf;
	}

	public void setJf(Integer jf) {
		this.jf = jf;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public Integer getPublished_num() {
		return published_num;
	}

	public void setPublished_num(Integer published_num) {
		this.published_num = published_num;
	}

	@Override
	public String toString() {
		return "About [xh=" + xh + ", jf=" + jf + ", start=" + start + ", published_num=" + published_num + "]";
	}
	
}

package org.ssm.dufy.entity;

import java.util.Date;

public class Store {

	//唯一主键xh
	private String xh;
	//收藏的bbs的xh
	private String bbs_xh;
	//收藏人xh
	private String ry_xh;
	//收藏时间
	private Date created;
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getBbs_xh() {
		return bbs_xh;
	}
	public void setBbs_xh(String bbs_xh) {
		this.bbs_xh = bbs_xh;
	}
	public String getRy_xh() {
		return ry_xh;
	}
	public void setRy_xh(String ry_xh) {
		this.ry_xh = ry_xh;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	@Override
	public String toString() {
		return "Store [xh=" + xh + ", bbs_xh=" + bbs_xh + ", ry_xh=" + ry_xh + ", created=" + created + "]";
	}
	
}

package org.ssm.dufy.entity;

import java.io.Serializable;

public class LikedCount implements Serializable {
	
    private String xh;

    private String bbs_xh;
   
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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

	public LikedCount(String bbs_xh, Integer count) {
		super();
		this.bbs_xh = bbs_xh;
		this.count = count;
	}

	public LikedCount() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LikedCountDTO [xh=" + xh + ", bbs_xh=" + bbs_xh + ", count=" + count + "]";
	}


}

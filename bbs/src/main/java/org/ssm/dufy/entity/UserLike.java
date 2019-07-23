package org.ssm.dufy.entity;

import java.io.Serializable;
import java.util.Date;

public class UserLike implements Serializable{

    private String xh;

    private String likedUserId;

    private String likedPostId;

    //点赞状态，默认没有点赞
    private  Integer status=LikedStatusEnum.UNLIKE.getCode();

    private String create_time;

    private String update_time;

    public UserLike(){}

    public UserLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }


    public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}


    public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(String likedUserId) {
        this.likedUserId = likedUserId;
    }

    public String getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(String likedPostId) {
        this.likedPostId = likedPostId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "UserLike [xh=" + xh + ", likedUserId=" + likedUserId + ", likedPostId=" + likedPostId + ", status="
				+ status + ", create_time=" + create_time + ", update_time=" + update_time + "]";
	}

    
}

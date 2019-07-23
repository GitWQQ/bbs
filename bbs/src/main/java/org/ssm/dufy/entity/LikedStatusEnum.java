package org.ssm.dufy.entity;

/**
 * 用户点赞状态枚举类
 */
public enum LikedStatusEnum {

    LIKE(1,"点赞"),
    UNLIKE(0,"取消点赞/未点赞"),;

    private Integer code;

    private String msg;

    LikedStatusEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

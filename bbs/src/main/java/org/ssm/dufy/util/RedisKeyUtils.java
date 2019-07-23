package org.ssm.dufy.util;

public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED="MAP_USER_LIKED";

    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT="MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户的id 和 点赞的人的id 作为key ,格式 AAAA::BBB
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId,String likedPostId){

        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(likedUserId);
        stringBuffer.append("::");
        stringBuffer.append(likedPostId);
        return  stringBuffer.toString();
    }
}

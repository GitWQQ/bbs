package org.ssm.dufy.service;

import org.ssm.dufy.entity.LikedCount;
import org.ssm.dufy.entity.LikedStatusEnum;
import org.ssm.dufy.entity.UserLike;

import java.util.List;

public interface RedisService {
	
    /**
     * 点赞 ，状态为 1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId,String likedPostId);

    /**
     * 取消点赞，状态将变为 0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikedFromRedis(String likedUserId,String likedPostId);

    /**
     * 从redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId,String likedPostId);

    /**
     * 该用户点赞数加 1
     * @param likedUserId
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 点赞数减 1
     * @param likedUserId
     */
    void decrementLikedCount(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCount> getLikedCountFromRedis();
    
    /**
     * 根据LikedUserId获取被点赞数
     * @param likeUserId
     * @return
     */
    Integer getLikedCountByLikedUserId(String likeUserId);
    
    /**
     * 将输出存入缓存中
     * @param key
     * @param likeUserId
     * @param value
     */
    void putLikedCount(String likeUserId,Object value);
    
    /**
     * 根据 被点赞的id和点赞的id获取是否被点赞的值
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    Object getUserLikeByLikeUserIdAndLikePostId(String likedUserId,String likedPostId);
    
}

package org.ssm.dufy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ssm.dufy.entity.UserLike;

import java.util.List;

public interface LikedService {

    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
	void save(UserLike userLike,String action);
    
    /**
     * 批量保存或修改
     * @param list
     * @return
     */
    List<UserLike> saveAll(List<UserLike> list);

    /**
     * 根据被点赞的人的id查询点赞列表（查询都是谁给这个人点过赞）
     * @param likedUserId
     * @param pageable
     * @return
     */
    Page<UserLike>  getLikedListByLikedUserId(String likedUserId, Pageable pageable);

    /**
     * 根据点赞人的id查询点赞列表，根据这个人的id查询这个人都给谁点过赞
     * @param likedPostId
     * @param pageable
     * @return
     */
    Page<UserLike> getLikedListByLikedPostId(String likedPostId,Pageable pageable);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    UserLike getUserLikeByLikedUserIdAndLikedPostId(String likedUserId,String likedPostId);

    /**
     * 将Redis中的点赞数量存入数据库
     */
    void  transLikedCountFromRedis2DB();
    
    /**
     * 将redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();


}

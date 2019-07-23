package org.ssm.dufy.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ssm.dufy.entity.UserLike;

import java.util.List;
import java.util.Map;


public interface UserLikeDao {

    void save(Map paramMap);
    
    void updateByParam(Map paramMap);

    List<UserLike> saveAll(List<UserLike> list);

    Page<UserLike> findByLikedUserIdAndStatus(String  likedUserId, Integer  status, Pageable pageable);

    Page<UserLike> findByLikedPostIdAndStatus(String  likedUserId, Integer  status, Pageable pageable);

    UserLike findByLikedUserIdAndLikedPostId(Map paramMap);

}

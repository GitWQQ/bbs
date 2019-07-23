package org.ssm.dufy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.ssm.dufy.entity.LikedCount;
import org.ssm.dufy.entity.LikedStatusEnum;
import org.ssm.dufy.entity.UserLike;
import org.ssm.dufy.service.LikedService;
import org.ssm.dufy.service.RedisService;
import org.ssm.dufy.util.RedisKeyUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikedService likeService;

    /**
     * 点赞 ，状态为 1
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人id
     */
    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key= RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key, LikedStatusEnum.LIKE.getCode());
    }

    /**
     * 取消点赞，状态将变为 0
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void unlikedFromRedis(String likedUserId, String likedPostId) {
        String key=RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.UNLIKE.getCode());
    }

    /**
     * 从redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key=RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }

    /**
     * 递增点赞数量点赞数加  1 
     */
    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,1);
    }

    /**
     * 递减点赞，点赞数减 1
     */
    @Override
    public void decrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,-1);
    }
    
    /**
     * 根据被点赞的id获取被点赞的数量
     * @param likeUserId
     * @return
     */
    public Integer getLikedCountByLikedUserId(String likeUserId){
    	return (Integer)redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likeUserId);
    }
    
    
    public void putLikedCount(String likeUserId,Object value){
    	redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likeUserId,value);
    }

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<UserLike> getLikedDataFromRedis(){
        Cursor<Map.Entry<Object,Object>> cursor=redisTemplate.opsForHash()
        		.scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list=new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry=cursor.next();
            String key=(String)entry.getKey();
            //分离出likeUserId,likePostId
            String [] split=key.split("::");
            String likedUserId=split[0];
            String likedPostId=split[1];
            Integer value=(Integer)entry.getValue();
            //组装UserLike对象
            UserLike userLike=new UserLike(likedUserId,likedPostId,value);
            list.add(userLike);
            //存到list后从redis中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
        }
        try {
			cursor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return list;
    }

    
    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    
    @SuppressWarnings("unchecked")
	@Override
    public List<LikedCount> getLikedCountFromRedis() {
    	Cursor<Map.Entry<Object,Object>> cursor=null;
    	List<LikedCount> list=new ArrayList<>();
    	try {
    		cursor=redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,ScanOptions.NONE);
    		while(cursor.hasNext()){
    			Map.Entry<Object,Object> map=cursor.next();
    			//将点赞数存储到ListCountDTO
    			String key=(String)map.getKey();
    			LikedCount dto=new LikedCount(key,(Integer)map.getValue());
    			list.add(dto);
    			//从redis中删除这条记录
    			//redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,key);
    		}
    	}catch (Exception e ) {
				e.printStackTrace();
    	}finally {
    		try {
				cursor.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return list;
    }

	@Override
	public Object getUserLikeByLikeUserIdAndLikePostId(String likedUserId, String likedPostId) {
		String key=RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
		Boolean existsKey=redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
		Object obj=null;
		if(existsKey){
			obj=redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
		}
		return obj;
	}
   

}

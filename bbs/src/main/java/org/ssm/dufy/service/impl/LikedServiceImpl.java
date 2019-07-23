package org.ssm.dufy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.LikeCountDao;
import org.ssm.dufy.dao.UserLikeDao;
import org.ssm.dufy.entity.*;
import org.ssm.dufy.service.BbsService;
import org.ssm.dufy.service.LikedService;
import org.ssm.dufy.service.RedisService;
import org.ssm.dufy.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikedServiceImpl implements LikedService {

    @Autowired
    UserLikeDao likeRepository;
    
    @Autowired
    RedisService redisService;

    @Autowired
    BbsService bbsService;

    @Autowired
    LikeCountDao likeCountDao;
    
    /**
     * 保存点赞记录
     * @param userLike
     * @return	
     */
    @Override
    public void save(UserLike userLike,String action){
    	Map<String,Object> paramMap=new HashMap<>();
    	if("add".equals(action)){
    		System.out.println("ADD");
    		String xh=CommonUtils.getXh();
        	paramMap.put("xh",xh);
        	paramMap.put("likedUserId",userLike.getLikedUserId());
        	paramMap.put("likedPostId",userLike.getLikedPostId());
        	paramMap.put("status", userLike.getStatus());
            paramMap.put("create_time",CommonUtils.getGxsjc());
            paramMap.put("update_time",CommonUtils.getGxsjc());
        	likeRepository.save(paramMap);
    	}else if("update".equals(action)){
    		System.err.println("status:"+userLike.getStatus());
    		paramMap.put("likedUserId",userLike.getLikedUserId());
        	paramMap.put("likedPostId",userLike.getLikedPostId());
        	paramMap.put("status", userLike.getStatus());
           likeRepository.updateByParam(paramMap);
    	}
    }
    

    /**
     * 批量保存或修改
     * @param list
     * @return
     */
    @Override
    public List<UserLike> saveAll(List<UserLike> list) {
        return  likeRepository.saveAll(list);
    }

    /**
     * 根据被点赞人的 id 查询点赞列表
     * @param likedUserId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return likeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(),pageable);
    }

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param likedPostId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return  likeRepository.findByLikedPostIdAndStatus(likedPostId,LikedStatusEnum.LIKE.getCode(),pageable);
    }

    /**
     *  通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @Override
    public UserLike getUserLikeByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
    	Map<String,Object> paramMap=new HashMap<>();
    	paramMap.put("likedUserId",likedUserId);
    	paramMap.put("likedPostId",likedPostId);
        return likeRepository.findByLikedUserIdAndLikedPostId(paramMap);
    }


    /**
     * 将 Redis中的点赞数量数据存入数据库
     */
    @Override
    public void transLikedCountFromRedis2DB() {
        List<LikedCount> list = redisService.getLikedCountFromRedis();
        for (LikedCount dto : list) {
            	
          LikedCount likedCount=likeCountDao.getLikeCountByParam(dto.getBbs_xh());
          //点赞数量属于无关紧要的操作，出错无需抛异常
          if (likedCount != null){
              //Integer likeNum = bs.getThumbs_up() + dto.getCount();
              Integer likeNUM=dto.getCount();
        	  Map<String,Object> paramMap=new HashMap<>();
        	  paramMap.put("bbs_xh",dto.getBbs_xh());
        	  paramMap.put("count",dto.getCount());
              //更新点赞数量
        	  likeCountDao.updateByParam(paramMap);
          }else{
              Map<String,Object> paramMap=new HashMap<>();
              paramMap.put("xh",CommonUtils.getXh());
              paramMap.put("bbs_xh",dto.getBbs_xh());
              paramMap.put("count",dto.getCount());
              likeCountDao.save(paramMap);
          }
       }
    }
    
    /**
     *  将Redis里的点赞数据存入数据库中
     *
     */
    @Override
    public void transLikedFromRedis2DB() {
    	
        List<UserLike> list=redisService.getLikedDataFromRedis();
        for(UserLike like :list){
            UserLike ul=getUserLikeByLikedUserIdAndLikedPostId(like.getLikedUserId(),like.getLikedPostId());
            if(ul==null){
                //没有记录，直接存入
                save(like,"add");
            }else{
                //有记录，需要更新
            	System.err.println("like.getStatus():"+like.getStatus());
                ul.setStatus(like.getStatus());
                save(ul,"update");
            }

        }
        
    }

}

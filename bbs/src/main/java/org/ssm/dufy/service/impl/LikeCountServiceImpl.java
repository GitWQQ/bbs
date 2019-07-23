package org.ssm.dufy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.LikeCountDao;
import org.ssm.dufy.entity.LikedCount;
import org.ssm.dufy.service.LikeCountService;

@Service
public class LikeCountServiceImpl  implements LikeCountService{

	@Autowired
	private LikeCountDao likeCountDao;
	
	@Override
	public LikedCount getLikeCountByParam(String bbs_xh) {
		LikedCount likedCount=likeCountDao.getLikeCountByParam(bbs_xh);
		return likedCount;
	}

}

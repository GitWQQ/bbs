package org.ssm.dufy.dao;

import java.util.List;
import java.util.Map;

import org.ssm.dufy.entity.LikedCount;

public interface LikeCountDao {

	public void save(Map paramMap);
	
	public LikedCount getLikeCountByParam(String bbs_xh);
	
	public void updateByParam(Map paramMap);
}

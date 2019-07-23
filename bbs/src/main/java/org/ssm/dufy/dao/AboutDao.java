package org.ssm.dufy.dao;

import java.util.Map;

import org.ssm.dufy.entity.About;

public interface AboutDao {
	
	public void updatePublishNUM(Map paramMap);
	
	public About queryAboutByXh(Map paramMap);
}

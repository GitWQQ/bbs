package org.ssm.dufy.dao;

import java.util.List;
import java.util.Map;

public interface StoreDao {
	
	/**
	 * 收藏bbs
	 * @param paramMap
	 */
	public void storeBbs(Map paramMap);
	
	/**
	 * 根据条件查询被收藏的bbs
	 * @param paramMap
	 * @return
	 */
	public List getStoreByParam(Map paramMap);
	
	
	/**
	 * 撤销收藏的bbs
	 * @param paramMap
	 */
	public void removeStoreBbs(Map paramMap);
	
}

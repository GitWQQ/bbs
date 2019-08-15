package org.ssm.dufy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ssm.dufy.entity.bbs;


public interface BbsService {

	public void addOrmodifyBBSContent(Map paramMap);
	
	public List getContentByParam(Map paramMap);
	
	public void removeBbsContent(Map<String,Object> paramMap);
	
	/**
	 * 查询发布所有的bbs的数量
	 * @return
	 */
	public Integer queryBbsCount(Map<String,Object> paramMap);
	
	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public List queryPagingBbs(Map paramMap);
	
	/**
	 * 查询当前用户发布的bbs的数量
	 * @param paramMap
	 * @return
	 */
	public Integer queryOwnBbsCount(Map paramMap);
	
	/**
	 * 跟帖评论
	 * @param paramMap
	 */
	public void addCommentBbs(Map<String,Object> paramMap);
	
	/**
	 * 根据条件获取bbs
	 * @param paramMap
	 * @return
	 */
	public List getBbbsByParam(Map paramMap);
	
	/**
	 * 收藏bbs操作
	 * @param paramMap
	 */
	public void addStoreBbs(Map paramMap);
	
	
	public void removeStoreBbs(Map paramMap);
	
	
	public void removeComment(Map paramMap);
	
	/**
	 * 根据条件查询被收藏的bbs
	 * @param paramMap
	 * @return
	 */
	public List getStoreBbsByParam(Map paramMap);
	
	bbs  findById(String id);
	
	void updateByParam(bbs bbs);
}

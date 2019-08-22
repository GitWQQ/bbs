package org.ssm.dufy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.AboutDao;
import org.ssm.dufy.dao.BbsDao;
import org.ssm.dufy.dao.StoreDao;
import org.ssm.dufy.entity.bbs;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.BbsService;
import org.ssm.dufy.util.CommonUtils;
import org.ssm.dufy.util.PageUtil;


@Service
public class BbsServiceImpl implements BbsService{

    @Autowired
    private BbsDao bbsDao;
    
    @Autowired
    private AboutDao aboutDao;

    @Autowired
    private StoreDao storeDao;
    
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addOrmodifyBBSContent(Map paramMap){
		
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		String xh=CommonUtils.getXh();
		
		if("add".equals(paramMap.get("action"))){
			//新增bbs
			//产生新的xh
			if(xh!=null && !"".equals(xh)){
				paramMap.put("xh",xh);
			}
			paramMap.put("author_id",user.getId());
			paramMap.put("created",CommonUtils.getGxsjc());
			paramMap.put("is_parent","1");
			paramMap.put("yxx","1");
			paramMap.put("thumbs_up",0);
			paramMap.put("about_xh",user.getAbout_xh());
			bbsDao.addBBSContent(paramMap);
			//更新tb_about表记录(贴子数量加1,积分加五)每发一个帖子积分加五
			Map<String,Object> aboutMap=new HashMap<>();
			aboutMap.put("jf",5);
			aboutMap.put("xh",user.getAbout_xh());
			aboutDao.updatePublishNUM(aboutMap);
		}else if("modify".equals(paramMap.get("action"))){
			//修改
			System.out.println("Modify");
			bbsDao.updateBbbsByParam(paramMap);
		}else if("comment".equals(paramMap.get("action"))){
			//跟帖评论
			if(xh !=null && !"".equals(xh))
			paramMap.put("xh",xh);
			paramMap.put("author_id",user.getId());
			paramMap.put("image_url","");
			paramMap.put("created",CommonUtils.getGxsjc());
			paramMap.put("is_parent","2");
			paramMap.put("parent_xh",paramMap.get("pl_xh"));
			paramMap.put("comment_time",CommonUtils.getGxsjc());
			paramMap.put("thumbs_up",0);
			paramMap.put("about_xh",user.getAbout_xh());
			paramMap.put("content",paramMap.get("content"));
			paramMap.put("yxx","1");
			bbsDao.addBBSContent(paramMap);
			//更新tb_about表记录,发布跟帖一个跟帖奖励3积分
			if(user.getAbout_xh()!=null){
				Map<String,Object> aboutMap=new HashMap<>();
				aboutMap.put("jf",3);
				aboutMap.put("xh",user.getAbout_xh());
				aboutDao.updatePublishNUM(aboutMap);
			}
			
		}else if("child_comment".equals(paramMap.get("action"))){
			//子跟帖（评论）
			if(xh !=null && !"".equals(xh))
			paramMap.put("xh",xh);
			paramMap.put("author_id",user.getId());
			paramMap.put("image_url","");
			paramMap.put("created",CommonUtils.getGxsjc());
			paramMap.put("is_parent","3");
			paramMap.put("parent_xh",paramMap.get("comment_xh"));
			paramMap.put("comment_time",CommonUtils.getGxsjc());
			paramMap.put("thumbs_up",0);
			paramMap.put("about_xh",user.getAbout_xh());
			paramMap.put("content",paramMap.get("comment_content"));
			paramMap.put("yxx","1");
			bbsDao.addBBSContent(paramMap);
		}
		
	}

	/*@Cacheable(value="bbsCache",key="method.name")*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getContentByParam(Map paramMap) {
		PageUtil page=new PageUtil();
		List result=null;
		if("comment".equals(paramMap.get("action"))){
			
			paramMap.put("is_parent","2");
			paramMap.put("parent_xh",paramMap.get("xh"));
			paramMap.put("startlimit",0);
			paramMap.put("limit",page.getLimit());
			paramMap.put("yxx","1");
			paramMap.remove("xh");
			result=bbsDao.queryCommentByParam(paramMap);
			
		}else{
			paramMap.put("is_parent","1");
			paramMap.put("startlimit",0);
			paramMap.put("limit",page.getLimit());
			paramMap.put("yxx","1");
			result=bbsDao.queryPagingContentByParam(paramMap);
		}
		return result;
	}

	@Override
	public void removeBbsContent(Map<String, Object> paramMap) {
		bbsDao.removeBbsContent(paramMap);
	}

	@Override
	public Integer queryBbsCount(Map<String, Object> paramMap) {
		//solr搜索
		Object action=paramMap.get("action");
		System.out.println("action:="+action);
		
		if("search".equals(paramMap.get("action"))){
			paramMap.put("like_title",paramMap.get("search_content"));
		}
		paramMap.put("is_parent","1");
		paramMap.put("yxx","1");
		
		if(action!=null && !"".equals(action) && "new".equals(action)){
			return bbsDao.queryNewBbsCount(paramMap);
		}
		return bbsDao.queryBbsCount(paramMap);
	}
	
	/**
	 * 分页查询
	 */
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public List queryPagingBbs(Map paramMap) {
		Subject subject=SecurityUtils.getSubject();
	  	User user=(User)subject.getPrincipal();
		List<bbs> list=null;
		if(paramMap !=null){
			//总数
			Integer count=Integer.parseInt(paramMap.get("count").toString());
			//当前页码
			Integer curr=Integer.parseInt(paramMap.get("curr").toString());
			//每页数量
			Integer limit=Integer.parseInt(paramMap.get("limit").toString());
			Integer startlimit=(curr-1)*limit;
			
			//查询当前登录的用户的所有发布信息
			if("1".equals(paramMap.get("own"))){
				paramMap.put("author_id",user.getId());
			}
			paramMap.put("startlimit",startlimit);
			paramMap.put("limit",limit);
			paramMap.put("yxx","1");
			paramMap.put("is_parent","1");
			
			if("new".equals(paramMap.get("action"))){
				list=bbsDao.queryNewPagingContentByParam(paramMap);
			}else{
				list=bbsDao.queryPagingContentByParam(paramMap);
			}
		}
		return list;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Integer queryOwnBbsCount(Map paramMap) {
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		if(user !=null){
			paramMap.put("author_id",user.getId());
			paramMap.put("is_parent",'1');
		}
		return bbsDao.queryOwnBbsCount(paramMap);
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List getBbbsByParam(Map paramMap) {
		
		if("child_comment".equals(paramMap.get("action"))){
			Map<String,Object> param=new HashMap<>();
			param.put("is_parent","3");
			param.put("parent_xh",paramMap.get("comment_xh"));
			param.put("yxx","1");
			return bbsDao.queryContentByParam(param);
		}else{
			return bbsDao.queryContentByParam(paramMap);
		}
	}

	@Override
	public void addCommentBbs(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 收藏bbs
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addStoreBbs(Map paramMap) {
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		String xh=CommonUtils.getXh();
		if(paramMap.get("pl_xh")!=null && !"".equals(paramMap.get("pl_xh"))){
			paramMap.put("bbs_xh",paramMap.get("pl_xh"));
			paramMap.put("xh",xh);
			paramMap.put("ry_xh",user.getId());
			paramMap.put("created",CommonUtils.getGxsjc());
			storeDao.storeBbs(paramMap);
		}
	}

	/**
	 * 根据条件查询被收藏的bbs
	 */
	@Override
	public List getStoreBbsByParam(Map paramMap) {
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		if(paramMap.get("xh")!=null && user!=null){
			paramMap.put("bbs_xh",paramMap.get("xh"));
			paramMap.put("ry_xh",user.getId());
			List list=storeDao.getStoreByParam(paramMap);
			return list;
		}
		return null;
	}
	
	/**
	 * 撤销删除收藏
	 */
	@Override
	public void removeStoreBbs(Map paramMap) {
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		if(paramMap.get("pl_xh")!=null){
			paramMap.put("bbs_xh",paramMap.get("pl_xh"));
			paramMap.put("ry_xh",user.getId());
			storeDao.removeStoreBbs(paramMap);
		}
	}
	
	@Override
	public bbs findById(String key) {
		return null;
	}

	@Override
	public void updateByParam(bbs bbs) {

	}

	@Override
	public void removeComment(Map paramMap) {
		Map<String,Object> param=new HashMap<>();
		System.err.println("comment_xh:"+paramMap.get("comment_xh"));
		if(paramMap.get("comment_xh")!=null){
			param.put("xh",paramMap.get("comment_xh"));
			param.put("yxx","1");
			param.put("is_parent","2");
			bbsDao.removeBbsContent(param);
		}
	}
}
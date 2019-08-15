package org.ssm.dufy.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ssm.dufy.entity.bbs;
import org.ssm.dufy.service.BbsService;
import org.ssm.dufy.service.LikedService;
import org.ssm.dufy.util.solr.Page;
import org.ssm.dufy.util.solr.SolrUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@EnableScheduling
public class LikeTask{

	
	private static final Logger log=LoggerFactory.getLogger(LikeTask.class);
	
	@Autowired
	private LikedService likeService;
	
	@Autowired
	private BbsService bbsService;
	
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer num=0;
	
	/**
	 * 每隔两小时执行一次
	 */
	@Scheduled(fixedRate=1000*60*60*2)
	//@Scheduled(fixedRate=1000*60)
	protected void transFromRedis2DB(){
			System.out.println("从Redis添加数据到DB");
			
			log.info("LikeTask-------{}",sdf.format(new Date()));
			//将redis里的点赞信息同步到数据库中
			likeService.transLikedCountFromRedis2DB();
			likeService.transLikedFromRedis2DB();		
	}
	
	
	/**
	 * 每隔一小时执行一次
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Scheduled(fixedRate=1000*60*30)
	protected void transFromDB2Solr() throws IOException, SolrServerException{
		System.out.println("从DB添加数据到Solr");
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("yxx","1");
		paramMap.put("is_parent","1");
		List list=bbsService.getBbbsByParam(paramMap);
		for (Object object : list) {
			bbs b=(bbs)object;
			Page page=new Page();
			page.setXh(b.getXh());
			page.setTitle(b.getTitle());
			page.setContent(b.getContent());
			page.setCreated(b.getCreated());
			page.setImage_url(b.getImage_url());
			page.setAbout_xh(b.getAbout_xh());
			page.setAuthor_id(b.getAuthor_id());
			page.setComment_time(b.getComment_time());
			page.setIs_parent(b.getIs_parent());
			page.setParent_xh(b.getParent_xh());
			page.setThumbs_up(b.getThumbs_up());
			SolrUtil.addIndex(page);
		}
	}
}

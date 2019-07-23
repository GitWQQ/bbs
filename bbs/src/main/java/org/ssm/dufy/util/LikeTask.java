package org.ssm.dufy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ssm.dufy.service.LikedService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@EnableScheduling
public class LikeTask{

	
	private static final Logger log=LoggerFactory.getLogger(LikeTask.class);
	
	@Autowired
	private LikedService likeService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer num=0;
	
	/**
	 * 每隔两小时执行一次
	 */
	//@Scheduled(fixedRate=1000*60*60*2)
	@Scheduled(fixedRate=1000*60)
	protected void transFromRedis2DB(){
			System.out.println(++num);
			log.info("LikeTask-------{}",sdf.format(new Date()));
			//将redis里的点赞信息同步到数据库中
			likeService.transLikedCountFromRedis2DB();
			likeService.transLikedFromRedis2DB();		
	}

}

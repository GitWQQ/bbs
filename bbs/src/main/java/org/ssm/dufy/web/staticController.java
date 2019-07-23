package org.ssm.dufy.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ssm.dufy.common.RedisTemplateUtil;
import org.ssm.dufy.entity.About;
import org.ssm.dufy.entity.LikedCount;
import org.ssm.dufy.entity.bbs;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.entity.UserLike;
import org.ssm.dufy.service.AboutService;
import org.ssm.dufy.service.BbsService;
import org.ssm.dufy.service.LikeCountService;
import org.ssm.dufy.service.LikedService;
import org.ssm.dufy.service.RedisService;
import org.ssm.dufy.service.UserService;
import org.ssm.dufy.util.CookieUtils;
import org.ssm.dufy.util.EncryptUtil;
import org.ssm.dufy.util.PageUtil;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@Controller
@RequestMapping("/static")
public class staticController {
    
	private static final Logger log=LoggerFactory.getLogger(SysController.class);

	
	@Autowired
	private BbsService bbsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AboutService aboutService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private LikeCountService likeCountService;
	
	@Autowired
	private LikedService likeService; 
	
	
	@RequestMapping("/test")
	public String toTest(){
		return "test";
	}
	
	
	 
    /**
     * 跳转到首页
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/index")
    public String index(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
    	ssoLogin(request, response);
    	return "index";
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping("/detail")
    public String toDetail(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
    	Map<String,Object> paramMap=getParamMap(request.getParameterMap());
    	Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
    	//根据xh获取指定的bbs,并返回结果集合
    	List list=bbsService.getContentByParam(paramMap);
    	//action添加参数
    	paramMap.put("action","comment");
    	if(paramMap.get("xh")!=null && !"".equals(paramMap.get("xh"))){
    		request.setAttribute("xh",paramMap.get("xh"));
    	}
    	
    	int status=0;
    	if(user !=null){
    		//根据 likedUserId,likedPostId从缓存中获取是否点赞
        	Object obj=redisService.getUserLikeByLikeUserIdAndLikePostId(paramMap.get("xh").toString(),user.getId());
        	if(obj==null){
        		//从数据库中获取
        		UserLike userLike=likeService.getUserLikeByLikedUserIdAndLikedPostId(paramMap.get("xh").toString(),user.getId());
        		if(userLike!=null){
        			//获取点赞状态，1是点赞0是未点赞
        			status=userLike.getStatus();
        		}
        	}else{
        		//从缓存中获取状态
        		status=(int)obj;
        	}
        }
    	if(status==1){
    		modelMap.put("likeStatus","like");
    		modelMap.put("likeStatusCode",1);
    	}else{
    		modelMap.put("likeStatus","unLike");
    		modelMap.put("likeStatusCode",0);
    	}
    	//获取点赞数，先从redis中获取，如果没有则从数据库中查询thumUp字段的值(点赞数)
    	Integer likeNum=redisService.getLikedCountByLikedUserId(paramMap.get("xh").toString());
    	if(likeNum==null){
    		//从数据库查
    		LikedCount likedCount=likeCountService.getLikeCountByParam(paramMap.get("xh").toString());
    		if(likedCount !=null){
    			redisService.putLikedCount(paramMap.get("xh").toString(),likedCount.getCount());
    		}
    	}
    	likeNum=redisService.getLikedCountByLikedUserId(paramMap.get("xh").toString());
    	
    	//根据条件查询被收藏的bbs
    	List storeList=bbsService.getStoreBbsByParam(paramMap);
    	if(storeList !=null){
    		modelMap.put("is_store",storeList.size());
    	}
    	//查找评论的bbs内容，并返回结果集合
    	List comments=bbsService.getContentByParam(paramMap);
    	
    	if(user!=null){
    		modelMap.put("currUser",user.getUsername());
    	}
    	modelMap.put("list",list);
    	modelMap.put("comments",comments);
    	modelMap.put("bbsCount",comments.size());
    	modelMap.put("likeCount",likeNum);
    	return "detail";
    }
    
    @SuppressWarnings("unchecked")
  	@RequestMapping("/publish")
      public String getContentByAuthorId( HttpServletRequest request,
  			HttpServletResponse response,ModelMap modelMap){
  		log.info("===getContentByAuthorId====");
  		Subject subject=SecurityUtils.getSubject();
  		User user=(User)subject.getPrincipal();
  		if(user==null){
  			return "index";
  		}else{
  			Map<String, Object> paramMap=new HashMap<>();
  			if(user.getId()!=null){
  				paramMap.put("author_id",user.getId());
  			}
  			List<bbs> list=bbsService.getContentByParam(paramMap);
  			Map<String,Object> aboutMap=new HashMap<>();
  			if(user.getAbout_xh()!=null){
  				aboutMap.put("xh",user.getAbout_xh());
  				About about=aboutService.queryAboutByXh(aboutMap);
  				modelMap.put("about",about);
  			}
  			modelMap.put("list",list);
  			return "publish";
  		}
  	}
    
    public void ssoLogin(HttpServletRequest request,HttpServletResponse response){
    	Subject subject=SecurityUtils.getSubject();
    	String res_u=CookieUtils.getCookieValByKey("sso_cookie_U",request);
		String res_p=CookieUtils.getCookieValByKey("sso_cookie_P",request);
		if(res_u !=null
				&& res_p !=null &&  !"".equals(res_p) && !"".equals(res_u)){
			//解密
			String sso_username=EncryptUtil.AESdecode(res_u,"666666");
			String sso_password=EncryptUtil.AESdecode(res_p,"666666");
			//
			//沒有登陸
			UsernamePasswordToken token=new UsernamePasswordToken(sso_username, sso_password);
			//token.setRememberMe(rememberMe);
			subject.login(token);
			//获取当前登录用户信息
			User currentUser=(User)subject.getPrincipal();
			//存session
			subject.getSession().setAttribute("user",currentUser);
			subject.getSession().setAttribute("username",currentUser.getUsername());
			subject.getSession().setTimeout(30*60*1000);
		}else{	
			if((subject!=null && subject.getSession()!=null)||(!"".equals(subject) && !"".equals(subject.getSession()))){
				subject.logout();
				Cookie[] cookies=request.getCookies();
				if(cookies !=null){
					for (Cookie cookie : cookies) {
						if(cookie.getName().equals("sso_cookie_U")){
							cookie.setValue("");
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
						if(cookie.getName().equals("sso_cookie_P")){
							cookie.setValue("");
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
				}
			}
		}
    }
    /**
     * 轉向到登陸頁面
     * @return
     */
    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
    	return "login";
    }
    /**
     * 权限验证失败跳转的页面，
     * 需要配合Spring的ExceptionHandler异常处理机制使用
     * @return
     */
    @RequestMapping("/unauthorized")
    public String toUnauthorizedPage(){
    	return "unauthorized";
    }
    
    /**
     * 跳转到404错误页面
     * @return
     */
    @RequestMapping("/err_404")
    public String  to404Page(){
    	return "/err/404";
    }
    
    /**
     * 跳转到500错误页面
     * @return
     */
    @RequestMapping("/err_500")
    public String to500Page(){
    	return "/err/500";
    }
    
    @RequestMapping("/error")
    public String toErrorPage(){
    	return "/err/error";
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping("/upload_json")
    public String toupload_json(){
    	return "upload_json";
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping("/file_manager_json")
    public String tofile_manager_json(){
    	return "file_manager_json";
    }

    

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/pagingCount",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getPageCount(HttpServletRequest request,HttpServletResponse response){
		log.info("=====pagingCount======");
		Map<String,Object> map=new HashMap<>();
	    Integer count=bbsService.queryBbsCount();
	    PageUtil page=new PageUtil();
	    page.setCount(count);
	    map.put("page", page);
	    return map;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getPagingBbs",method=RequestMethod.GET)
	@ResponseBody
	public List getPagingBbs(HttpServletRequest request,HttpServletResponse response
			){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		PageUtil pageUtil=new PageUtil();
		Integer curr=Integer.parseInt(paramMap.get("curr").toString());
		Integer limit=pageUtil.getLimit();
		List list=null;
		//从缓存中获取数据，如果获取的数据为空，则从数据库中获取数据
		//list=redisTemplate.opsForList().range("getPagingBbs",(curr-1)*limit,curr*limit-1);
		//if(list.isEmpty()){
			//从数据库中获取数据，并将数据放到缓存中
			list=bbsService.queryPagingBbs(paramMap);
			//redisTemplate.opsForList().rightPushAll("getPagingBbs",list);
			//redisTemplate.expire("getPagingBbs",60*60*2,TimeUnit.SECONDS);
		//}
		return list;
	}
	
	
	@RequestMapping("/upload")
	public String toUpload(){
		return "upload";
	}
	
    /**
	 * 获取request里的参数，
	 * 并且封装到Map集合中
	 * @param map
	 * @return
	 */
	private Map<String, Object> getParamMap(Map<String, String[]> map){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			if (map.get(key) instanceof String[]) {
				if (((String[]) map.get(key)).length > 0) {					
					paramMap.put(key, ((String[]) map.get(key))[0]);
				}
			}
		} 
		return paramMap;
	}
}

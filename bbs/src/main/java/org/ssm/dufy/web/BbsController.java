package org.ssm.dufy.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.ssm.dufy.entity.bbs;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.BbsService;
import org.ssm.dufy.service.RedisService;
import org.ssm.dufy.util.PageUtil;
import org.ssm.dufy.util.SessionUtil;

@Controller
@RequestMapping("/bbs")
public class BbsController {

	private static final Logger log=LoggerFactory.getLogger(SysController.class);
	
	@Autowired
	private BbsService bbsService;
	
	@Autowired
	private RedisService redisService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("/publishOrmodify")
	@ResponseBody
	public Map<String,Object> toPublish(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<>();
		if(paramMap.isEmpty()){
			result.put("message","发布内容为空发布失败");
		}else{
			bbsService.addOrmodifyBBSContent(paramMap);
			result.put("message","发布成功");
		}
		return result;
	}
	
	/**
	 * 跟帖评论
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/republish")
	@ResponseBody
	public Map<String,Object> toRePublish(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<>();
		bbsService.addOrmodifyBBSContent(paramMap);
		result.put("msg","跟帖成功!您的账户将增加3积分");
		return result;
	}
		
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modifyPublish",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toModifyPublish(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<>();
		if(paramMap.isEmpty()){
			result.put("message","修改失败");
		}else{
			bbsService.addOrmodifyBBSContent(paramMap);
			result.put("message","修改成功！");
		}
		return result;
	}
	
	
	@RequestMapping("/getContentByAuthorId")
	@ResponseBody
	public String getContentByAuthorId( HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){
		log.info("===getContentByAuthorId====");
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		Map<String, Object> paramMap=new HashMap<>();
		if(user.getId()!=null){
			paramMap.put("author_id",user.getId());
		}
		List list=bbsService.getContentByParam(paramMap);
		modelMap.put("list",list);
		return "publish";
	}
	
	@RequestMapping(value="/modifyBbsContent",method=RequestMethod.GET)
	public String modifyBbsContent(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		if(paramMap.get("xh")!=null){
			List list=bbsService.getContentByParam(paramMap);
			modelMap.put("bbs",(bbs)list.get(0));
		}
		return "modifyBbs";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="removeBbsContent",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> removeBbs(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,String> result=new HashMap<>();
		String xh=paramMap.get("xh").toString();
		if(xh!=null){
			bbsService.removeBbsContent(paramMap);
			result.put("message","删除成功!");
		}else{
			result.put("message","删除失败");
		}
		return result;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value="/ownpagingCount",method=RequestMethod.GET)
	@ResponseBody
	public Map getOwnPagingCount(HttpServletRequest request,HttpServletResponse response){
		log.info("=============ownpagingCount==============");
		Map paramMap=getParamMap(request.getParameterMap());
		Map map=new HashMap<>();
		Integer count=bbsService.queryOwnBbsCount(paramMap);
		PageUtil page=new PageUtil();
		page.setCount(count);
		map.put("page",page);
		return  map;
	}
	
	
	@RequestMapping("/fileUpload")
	@ResponseBody
	public Map bbsFileUpload(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		System.out.println("======bbsFileUpload=====");
		
		//判断文件是否为空
		if(file!=null){
			
			ServletContext application=request.getSession().getServletContext();
			//上传文件保存路径
			String savePath=application.getRealPath("/")+"uploadFile/";
			String saveurl=application.getRealPath("/")+"uploadFile/";
			
			//定义允许上传的文件扩展名
			Map<String,String> extMap=new HashMap<>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("flash", "swf,flv");
			//2M
			long maxSize = 2097152;
			
			//
			/*if(file.getSize()>maxSize){
				return getError("上传文件超出允许范围2M");
			}*/
			
			String dirName = request.getParameter("dir");
			
			if(dirName==null){
				dirName="file";
			}
			
			savePath+=dirName+'/';
			saveurl+=dirName+'/';
			
			File saveDirFile = new File(savePath);
			
			if (!saveDirFile.exists()) {
				  saveDirFile.mkdirs();
			}
			
			//创建文件夹
			SimpleDateFormat sformat=new SimpleDateFormat("yyyyMMdd");
			String ymd = sformat.format(new Date());
			savePath+=ymd+'/';
			saveurl+=ymd+'/';
			//判断文件夹是否存在，如果不存在则创建
			File dirFile=new File(savePath);
			if(!dirFile.exists()){
				System.out.println("===========dirFile==============");
				dirFile.mkdirs();
			}
			
			//获原始取文件名后缀
			String ext=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1).toLowerCase();
			if(!Arrays.asList(extMap.get(dirName).split(",")).contains(ext)){
				return getError("上传文件扩展名是不允许的扩展名。\n只允许"
					       + extMap.get(dirName) + "格式。");
			}
			//产生新的文件名
			SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
			String fileNewName=sf.format(new Date());
			fileNewName=fileNewName+'.'+ext;
			File uploadFile=new File(savePath+fileNewName);
			file.transferTo(uploadFile);
			System.out.println("savePath:"+savePath);
			System.out.println("saveUrl:"+saveurl);
			String fileUrl=saveurl+fileNewName;
			SessionUtil.createSession("fileUrl",fileUrl,1000*60*30);
			//
			 Map<String, Object> msg = new HashMap<String, Object>();
			 msg.put("msg","上传成功");
			 msg.put("url", saveurl + fileNewName);
			 return msg;
			 
		}else{
			return getError("请选择文件");
		}
		
	}
	
	public Map<String,String> getError(String msg){
		Map<String,String> map=new HashMap<>();
		map.put("msg",msg);
		return map;
	}
	
	
	/**
	 * 收藏/取消收藏bbs
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/store",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> storeBbs(HttpServletRequest request,HttpServletResponse response){
	
		Map<String,String> result=new HashMap<>();
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		bbsService.addStoreBbs(paramMap);
		result.put("msg","收藏成功");
		return result;
	}
	
	
	@RequestMapping(value="/remove_store",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> remove_storeBbs(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,String> result=new HashMap<>();
		bbsService.removeStoreBbs(paramMap);
		result.put("msg","撤销收藏成功");
		return result;
	}
	
	
	@RequestMapping(value="/remove_comment",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> remove_comment(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		System.out.println("comment_xh:"+paramMap.get("comment_xh"));
		Map<String,String> result=new HashMap<>();
		bbsService.removeComment(paramMap);
		result.put("msg","撤销收藏成功");
		return result;
	}
	
	/**
	 * 点赞操作
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/likeOrUnLike")
	@ResponseBody
	public Map<String,Object> like(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<>();
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();
		if("like".equals(paramMap.get("action"))){
			redisService.saveLiked2Redis(paramMap.get("xh").toString(),user.getId());
			redisService.incrementLikedCount(paramMap.get("xh").toString());
			Integer count=redisService.getLikedCountByLikedUserId(paramMap.get("xh").toString());
			result.put("count",count);
		}
		if("unLike".equals(paramMap.get("action"))){
			redisService.unlikedFromRedis(paramMap.get("xh").toString(),user.getId());
			redisService.decrementLikedCount(paramMap.get("xh").toString());
			Integer count=redisService.getLikedCountByLikedUserId(paramMap.get("xh").toString());
			result.put("count",count);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getChildComment",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getChildComment(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<String,Object>();
		List list=bbsService.getBbbsByParam(paramMap);
		System.out.println("list:="+list);
		result.put("data",list);
		System.out.println(result);
		return result;
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
	
	public static void main(String[] args) {
		String fileName="11111111111111.jpg";
		System.out.println(fileName.substring(fileName.lastIndexOf('.')+1));
	}
}

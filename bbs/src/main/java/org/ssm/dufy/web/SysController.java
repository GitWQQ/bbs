package org.ssm.dufy.web;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.util.CookieUtils;
import org.ssm.dufy.util.EncryptUtil;
import org.ssm.dufy.util.SessionUtil;


@Controller
@RequestMapping("/sys")
public class SysController {
	
	
	
	private static final Logger log=LoggerFactory.getLogger(SysController.class);
 
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/doLogin")
	@ResponseBody
	public Map<String,Object> doLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap=getParamMap(request.getParameterMap());
		Map<String,Object> result=new HashMap<>();
		String username=paramMap.get("username").toString();
		String password=paramMap.get("password").toString();
		boolean rememberMe=Boolean.parseBoolean(paramMap.get("rememberMe").toString());
		Subject subject=SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){//没有登录
			try{
				UsernamePasswordToken token=new UsernamePasswordToken(username, password);
				token.setRememberMe(rememberMe);
				subject.login(token);
				//
				CookieUtils.createCookie("sso_cookie_U",EncryptUtil.AESencode(username,"666666"),request,response);
				CookieUtils.createCookie("sso_cookie_P",EncryptUtil.AESencode(password,"666666"),request,response);

				//获取当前登录用户信息
				User currentUser=(User)subject.getPrincipal();
				//存session
				subject.getSession().setAttribute("user",currentUser);
				subject.getSession().setAttribute("username",currentUser.getUsername());
				subject.getSession().setTimeout(30*60*1000);
				
				log.info("用户【"+username+"】登录成功");
				result.put("status",200);
			}catch(UnknownAccountException uae){
				log.info("用户【"+username+"】不存在");
				result.put("status",201);
				result.put("message","用户不存在,");
			}catch(IncorrectCredentialsException ice){
				log.info("用户【"+username+"】密码不正确");
				result.put("status",202);
				result.put("message","密码不正确,");
			}catch(LockedAccountException lae){
				log.info("用户【"+username+"】账号被锁定");
				result.put("status",203);
				result.put("message","账号被锁定");
			}catch(ExcessiveAttemptsException eae){
				log.error("密码尝试限制");
				result.put("status",204);
				result.put("message","密码尝试限制");
			}
			
		}else{
			result.put("message","当前已有用户登录,如要登录请先退出后 再进行登录");
		}
		return result;
	}
	
	/**
	 * 登出
	 * @return 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String,String> logout(HttpServletRequest request,HttpServletResponse response) throws  ServletException, IOException{
		Map<String,String> result=new HashMap<>();
		Subject subject=SecurityUtils.getSubject();
		if(subject!=null && subject.getSession()!=null){
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
		result.put("message","退出成功");
		return result;
	}

	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/fileUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> fileUpload(HttpServletRequest request,HttpServletResponse response){
		System.out.println("=====IMG=fileUpload========");
		ServletContext application=request.getSession().getServletContext();
		String savePath=application.getRealPath("/")+"uploadFile/";
		//文件保存目录url
		String saveUrl=request.getContextPath()+"/uploadFile/";
		//定义允许上传的文件扩展名
		HashMap<String,String> extMap=new HashMap<String,String>();
		//定义上传文件扩展名
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");	
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		
		// 最大文件大小 2M
		long maxSize = 2097152;
		
		response.setContentType("text/html; charset=UTF-8");
		if (!ServletFileUpload.isMultipartContent(request)) {
			 return getError("请选择文件。");
		}
		
		 // 检查目录
		File uploadDir=new File(savePath);
		if(!uploadDir.isDirectory()){
			return getError("上传的目录不存在");
		}
		
		//检查目录权限
		if(!uploadDir.canWrite()){
			return getError("上传目录没有写权限");
		}
		
		String dirName = request.getParameter("dir");
		
		if (dirName == null) {
			 dirName = "image";
		}
		 
		
		if (!extMap.containsKey(dirName)) {
			 return getError("目录名不正确。");
		}
		
		// 创建文件夹
		
		savePath += dirName + "/";
		
		saveUrl += dirName + "/";
		 
		File saveDirFile = new File(savePath);
		
		if (!saveDirFile.exists()) {
			  saveDirFile.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			 dirFile.mkdirs();
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator item = multipartRequest.getFileNames();
		
		while (item.hasNext()) {
			String fileName = (String) item.next();
			MultipartFile file = multipartRequest.getFile(fileName);
			// 检查文件大小
			if (file.getSize() > maxSize) {
				long currSize=file.getSize();
				double size=(currSize/1024/1024.0);
				return getError("上传文件大小超过限制(2M),上传的文件有"+size+"M");
			}
			// 检查扩展名
			String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			
			if (!Arrays. asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				 return getError("上传文件扩展名是不允许的扩展名。\n只允许"
				       + extMap.get(dirName) + "格式。");
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			 try {
				 File uploadedFile = new File(savePath+newFileName);
				 file.transferTo(uploadedFile);
			 
			 }catch(Exception e){
				 return getError("上传文件失败。");
			 }
			 Map<String, Object> msg = new HashMap<String, Object>();
			 msg.put("error", 0);
			 msg.put("url", saveUrl + newFileName);
			 //图片路径存到session中
			 String imgUrl=saveUrl+newFileName;
			 SessionUtil.createSession("imgUrl",imgUrl,1000*60*30);
			 return msg;
		}
		return null;
	}
	
	public Map<String,Object> getError(String message){
		 Map<String, Object> msg = new HashMap<String, Object>();
		 msg.put("error", 1);
		 msg.put("message", message);
		 return msg;
	}
	
	@RequestMapping("/fileManager")
	public String toFileManager(){
		return "file_manager_json";
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
		System.out.println(2097152/1024/1024);
	}
}

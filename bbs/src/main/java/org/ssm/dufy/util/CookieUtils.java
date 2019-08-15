package org.ssm.dufy.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	
	/**
	 * 创建cookie
	 * @param cookie_key
	 * @param cookie_val
	 * @param request
	 * @param response
	 */
	public static void createCookie(String cookie_key,String cookie_val,
			HttpServletRequest request,HttpServletResponse response){
		Cookie cookie=new Cookie(cookie_key,cookie_val);
		//cookie.setMaxAge(60*60*1);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 根据key获取Cookie的值
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookieValByKey(String key,HttpServletRequest request){
		String value=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(key)){
					value=cookie.getValue();
				}
			}
		}
		System.out.print("value:"+value);
		return value;
	}
}

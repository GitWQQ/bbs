package org.ssm.dufy.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionUtil {

	private SessionUtil(){
		
	}
	
	public static Session getInstance(){
		Subject subject=SecurityUtils.getSubject();
		Session session=null;
		if(session==null){
			session=subject.getSession();
		}
		return session;
	}
	
	public static Session createSession(String key,Object value,long timeout){
		Session session=getInstance();
		session.setAttribute(key,value);
		session.setTimeout(timeout);		
		return session;		
	}
	
	public static Object getSessionValue(String key){
		Session session=getInstance();
		Object value=session.getAttribute(key);
		return value;
	}
	
}

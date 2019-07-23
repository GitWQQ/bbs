package org.ssm.dufy.web;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SingleLogin implements HttpSessionListener{

	
	private static HashMap hUserName=new HashMap<>();
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
	}

	public static boolean isAlreadyEnter(HttpSession session,String sUserName){
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAa");
		boolean falg=false;
        // 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hUserName中)  
		if(hUserName.containsValue(sUserName)){
			
		}else{// 如果该用户没登录过，直接添加现在的sessionID和username  

		}
		return falg;
	}
}

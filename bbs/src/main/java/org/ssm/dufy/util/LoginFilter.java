package org.ssm.dufy.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssm.dufy.entity.User;

public class LoginFilter implements Filter{

	Logger log=LoggerFactory.getLogger(LoginFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse)response;
		Subject subject=SecurityUtils.getSubject();
		Session session=subject.getSession();
		
		Object username=session.getAttribute("username");
		if(username!=null){
			log.info("已经登录了");
			chain.doFilter(req, resp);
		}else{
			log.info("还没有登录，跳转到首页");
			//没有登录跳转到首页
			req.getRequestDispatcher("/static/index").forward(req, resp);
			
		}
	
		
	}

	@Override
	public void destroy() {
		
	}

}

package org.ssm.dufy.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import freemarker.core.Environment;

public class Log4jConfigListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path=Environment.class.getResource("").getPath();
		String webAppPath=path.substring(0,path.toUpperCase().lastIndexOf("/WEB-INF/")).replaceAll("%20","");
		System.err.println(webAppPath+"logs/log.log");
		System.setProperty("webapp",webAppPath+"logs/log.log");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}

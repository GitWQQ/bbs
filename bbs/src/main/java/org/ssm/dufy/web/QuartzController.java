package org.ssm.dufy.web;

import java.util.Date;

public class QuartzController {

	 public void execute(){
         System.out.println("Quartz的任务调度！！！"+(new Date()).toString());
     }
}

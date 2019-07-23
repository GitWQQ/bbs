package org.ssm.dufy.common;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SpringTaskDemo {

	@Scheduled(fixedRate=1000*60*60*2)
	public void demo(){
		System.out.println("hello world");
	}
}

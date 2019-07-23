package org.ssm.dufy.common;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Service;

@Service("jobFactory")
public class JobFactory extends AdaptableJobFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
		Object jobInstance=super.createJobInstance(bundle);
		autowireCapableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}

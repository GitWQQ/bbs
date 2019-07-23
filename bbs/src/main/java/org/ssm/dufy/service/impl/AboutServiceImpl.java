package org.ssm.dufy.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.AboutDao;
import org.ssm.dufy.entity.About;
import org.ssm.dufy.service.AboutService;

@Service
public class AboutServiceImpl implements AboutService {
	
	@Autowired
	private AboutDao aboutDao;
	
	@Override
	public About queryAboutByXh(Map paramMap) {
		About about=aboutDao.queryAboutByXh(paramMap);
		if(about!=null){
			return about;
		}
		return null;
	}

	
}

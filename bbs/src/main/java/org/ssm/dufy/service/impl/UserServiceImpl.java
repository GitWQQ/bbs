package org.ssm.dufy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.UserMapper;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByParam(Map param) {
		return userMapper.getUserByParam(param);
	}

}

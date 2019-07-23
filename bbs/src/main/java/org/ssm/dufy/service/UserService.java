package org.ssm.dufy.service;

import java.util.List;
import java.util.Map;

import org.ssm.dufy.entity.User;

public interface UserService {
	
	public List<User> getUserByParam(Map param);
}

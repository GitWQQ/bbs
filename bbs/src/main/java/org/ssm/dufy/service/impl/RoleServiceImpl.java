package org.ssm.dufy.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.RoleMapper;
import org.ssm.dufy.entity.Role;
import org.ssm.dufy.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByParam(String username) {
		if(username!=null && !"".equals(username)){
			return roleMapper.getRoleByParam(username);
		}
		return null;
	}
	
	/**
	 * 获取所有的角色
	 */
	@Override
	public List getRoles(Map<String, Object> param) {
		List result=roleMapper.getRoles(param);
		return result;
	}

}

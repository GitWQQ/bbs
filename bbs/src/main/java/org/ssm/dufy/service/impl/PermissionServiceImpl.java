package org.ssm.dufy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.PermissionMapper;
import org.ssm.dufy.entity.Permission;
import org.ssm.dufy.service.PermissionService;


@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public List<Permission> getPermissionByParam(String rolename) {
		if(rolename !=null && !"".equals(rolename)){
			return permissionMapper.getPermissionByParam(rolename);
		}
		return null;
	}
}

package org.ssm.dufy.service;

import java.util.List;
import java.util.Map;

import org.ssm.dufy.entity.Role;

public interface RoleService {
	
	public List<Role> getRolesByParam(String username);
	
	public List getRoles(Map<String,Object> param);

}

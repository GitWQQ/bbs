package org.ssm.dufy.dao;

import java.util.List;
import java.util.Map;

import org.ssm.dufy.entity.Role;


public interface RoleMapper {

	public List<Role> getRoleByParam(String username);
	
	public List<Role> getRoles(Map<String,Object> map);

}

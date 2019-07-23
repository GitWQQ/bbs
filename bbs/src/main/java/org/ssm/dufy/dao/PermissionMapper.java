package org.ssm.dufy.dao;

import java.util.List;
import org.ssm.dufy.entity.Permission;

public interface PermissionMapper {
	
	public List<Permission> getPermissionByParam(String rolename);
}

package org.ssm.dufy.service;

import java.util.List;

import org.ssm.dufy.entity.Permission;

public interface PermissionService {
	
  public List<Permission> getPermissionByParam(String roleName);
  
}

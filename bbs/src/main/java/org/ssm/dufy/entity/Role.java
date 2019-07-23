package org.ssm.dufy.entity;

import java.io.Serializable;
import java.util.List;


public class Role implements Serializable {
	
	private String id;
	private String roleName;
	private User user;
	private List<Permission> permissions;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", user=" + user + ", permissions=" + permissions + "]";
	}	
	
}

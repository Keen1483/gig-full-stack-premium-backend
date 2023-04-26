package com.devskills.gigfullstackpremium.service;

import java.util.List;

import com.devskills.gigfullstackpremium.domain.Role;

public interface RoleService {
	List<Role> getRoles();
	Role getRole(String name);
	Role saveRole(Role role);
	Role updateRole(Long id, Role role);
	void deleteRole(String name);
}

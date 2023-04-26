package com.devskills.gigfullstackpremium.service;

import java.util.Set;
import java.util.List;

import com.devskills.gigfullstackpremium.domain.Role;
import com.devskills.gigfullstackpremium.domain.User;

public interface UserService {
	List<User> getUsers();
	User getUser(String username);
	User saveUser(User user);
	User updateUser(Long id, User user);
	void deleteUser(String username);
	User addRolesToUser(String username, Set<Role> roles);
	User checkPassword(String password, User user);
}

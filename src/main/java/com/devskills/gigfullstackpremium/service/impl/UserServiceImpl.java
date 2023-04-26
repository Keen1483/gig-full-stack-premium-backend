package com.devskills.gigfullstackpremium.service.impl;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devskills.gigfullstackpremium.exception.BadContentException;
import com.devskills.gigfullstackpremium.exception.ResourceNotFoundException;
import com.devskills.gigfullstackpremium.domain.Role;
import com.devskills.gigfullstackpremium.domain.User;
import com.devskills.gigfullstackpremium.service.UserService;
import com.devskills.gigfullstackpremium.repository.RoleRepository;
import com.devskills.gigfullstackpremium.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if (optionalUser.isPresent()) {
			log.info("User found in the database: {}", username);
		} else {
			log.error("User {} not found in the database", username);
			throw new UsernameNotFoundException("User not found in the database");
		}
		
		User user = optionalUser.get();
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {}", username);
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			log.error("User {} not found in the database", username);
			throw new ResourceNotFoundException("User " + username + " not found in the database");
		}
	}

	@Override
	public User saveUser(User user) {
		if (user.getEmail() == null || user.getPassword() == null) {
			throw new BadContentException("Cannot create empty user: " + user);
		} else {
			String username = user.getUsername() == null ? user.getEmail() : user.getUsername();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreatedAt(LocalDateTime.now());
			log.info("Saving new user {} in the database", user.getUsername());
			return userRepo.save(user);
		}
	}

	@Override
	public User updateUser(Long id, User user) {
		Optional<User> optionalUser = userRepo.findById(id);
		
		if (optionalUser.isPresent()) {
			User currentUser = optionalUser.get();
			log.info("Updating user: {}", currentUser.getUsername());
			
			String email = user.getEmail();
			if (email != null && !email.equals(currentUser.getEmail())) {
				currentUser.setEmail(email);
			}
			
			String name = user.getName();
			if (name != null && !name.equals(currentUser.getName())) {
				currentUser.setName(name);
			}
			
			String username = user.getUsername();
			if (username != null && !username.equals(currentUser.getUsername())) {
				currentUser.setUsername(username);
			}
			
			String password = user.getPassword();
			if (password != null && !passwordEncoder.matches(password, currentUser.getPassword())) {
				currentUser.setPassword(passwordEncoder.encode(password));
			}
			
			currentUser.setEditedAt(LocalDateTime.now());
			
			// Save updated data before adding new roles because saving user can be failed
			User updatedUser = userRepo.save(currentUser);
			
			// If user is successfully updated, we can add new roles if they exist
			Set<Role> roles = user.getRoles();
			//roles.removeAll(updatedUser.getRoles());
			if (!roles.isEmpty()) {
				Set<Role> currentRoles = updatedUser.getRoles();
				updatedUser.getRoles().removeAll(currentRoles);
				updatedUser = userRepo.save(updatedUser);
				return this.addRolesToUser(updatedUser.getUsername(), roles);
			}
			
			return updatedUser;
		} else {
			log.error("User {} not found in database", user.getUsername());
			throw new ResourceNotFoundException("User " + user.getUsername() + " not found in database");
		}
	}

	@Override
	public void deleteUser(String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			log.warn("Deleting user {} in the database", username);
			User user = optionalUser.get();
			
			// Remove Posts
			user.getPosts().removeAll(user.getPosts());
			user = userRepo.save(user);
			
			// Remove Comments
			user.getComments().removeAll(user.getComments());
			user = userRepo.save(user);
			
			// Finally, delete User
			userRepo.deleteByUsername(username);
		} else {
			log.error("User {} not found in the database", username);
			throw new ResourceNotFoundException("User " + username + " not found in the database");
		}

	}
	
	@Override
	public User checkPassword(String password, User user) {
		Optional<User> optionalUser = userRepo.findByUsername(user.getUsername());
		if (optionalUser.isPresent()) {
			User currentUser = optionalUser.get();
			if (passwordEncoder.matches(password, currentUser.getPassword())) {
				log.info("Password {} matches encoder password: {}", password, currentUser.getPassword());
				return currentUser;
			} else {
				log.error("{} is not the password of the user {}", password, user.getUsername());
				throw new ResourceNotFoundException(password + " is not the password of the user " + user.getUsername());
			}
		} else {
			log.error("User {} not found in the database", user.getUsername());
			throw new ResourceNotFoundException("User " + user.getUsername() + " not found in the database");
		}
	}

	@Override
	public User addRolesToUser(String username, Set<Role> roles) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if (optionalUser.isPresent() && !roles.isEmpty()) {
			User user = optionalUser.get();
			
			for (Role role : roles) {
				if (role != null && roleRepo.findByName(role.getName()).isPresent()) {
					log.info("Adding role {} to user {}", role.getName(), username);
					// Here, role ID can be null and role name can already exist in the database
					user.getRoles().add(roleRepo.findByName(role.getName()).get());
				} else {
					log.warn("Role {} not found in the database", role);
				}
			}
			return saveUser(user);
		} else {
			log.error("User {} not found in the database or roles collection {} is empty", username, roles);
			throw new ResourceNotFoundException("User " + username + " not found in the database or roles collection " + roles.toString() + " is empty");
		}
	}

}

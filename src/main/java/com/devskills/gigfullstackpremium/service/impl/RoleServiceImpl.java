package com.devskills.gigfullstackpremium.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devskills.gigfullstackpremium.exception.BadContentException;
import com.devskills.gigfullstackpremium.exception.ResourceNotFoundException;
import com.devskills.gigfullstackpremium.domain.Role;
import com.devskills.gigfullstackpremium.repository.RoleRepository;
import com.devskills.gigfullstackpremium.repository.UserRepository;
import com.devskills.gigfullstackpremium.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepo;
	private final UserRepository userRepo;

	@Override
	public List<Role> getRoles() {
		log.info("Fetching all roles");
		return roleRepo.findAll();
	}

	@Override
	public Role getRole(String name) {
		log.info("Fetching role {}", name);
		Optional<Role> optionalRole = roleRepo.findByName(name);
		
		if (optionalRole.isPresent()) {
			return optionalRole.get();
		} else {
			log.error("Role {} not found in database", name);
			throw new ResourceNotFoundException("Role " + name + " not found in database");
		}
	}

	@Override
	public Role saveRole(Role role) {
		if (role == null || role.getName() == null) {
			log.error("Cannot create empty role");
			throw new BadContentException("Cannot create empty role");
		} else {
			role.setName(role.getName().toUpperCase());
			
			if (role.getName().matches("^((?!ROLE)(?!ROLE_)[A-Z0-9_])+$")) {
				log.info("Saving new role {} in the database", role.getName());
				role.setName("ROLE_" + role.getName());
				role.setCreatedAt(LocalDateTime.now());
				return roleRepo.save(role);
			} else {
				log.error("Role {} is in a bad format", role.getName());
				throw new BadContentException("Role " + role.getName() + " is in a bad format");
			}
		}
	}

	@Override
	public Role updateRole(Long id, Role role) {
		Optional<Role> optionalRole = roleRepo.findById(id);
		
		if (optionalRole.isPresent()) {
			Role currentRole = optionalRole.get();
			log.info("Updating role: {}", currentRole.getName());
			
			String name = role.getName();
			if (name != null && !name.equals(currentRole.getName())) {
				currentRole.setName(name);
				currentRole.setEditedAt(LocalDateTime.now());
			}
			return roleRepo.save(currentRole);
		} else {
			log.error("Role {} not found in the database", role.toString());
			throw new ResourceNotFoundException("Role " + role.toString() + " not found in the database");
		}
	}

	@Override
	public void deleteRole(String name) {
		Optional<Role> optionalRole = roleRepo.findByName(name);
		
		if (optionalRole.isPresent()) {
			log.warn("Deleting role {} in the database", name);
			Role role = optionalRole.get();
			role.getUsers().forEach(user -> user.removeRole(role));
			userRepo.saveAll(role.getUsers());
			roleRepo.deleteByName(name);
		} else {
			log.error("Role {} not found in the database", name);
			throw new ResourceNotFoundException("Role " + name + " not found in the database");
		}

	}

}

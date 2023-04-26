package com.devskills.gigfullstackpremium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devskills.gigfullstackpremium.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
	void deleteByName(String name);
}

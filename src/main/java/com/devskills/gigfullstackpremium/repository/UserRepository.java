package com.devskills.gigfullstackpremium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devskills.gigfullstackpremium.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	void deleteByUsername(String username);
}

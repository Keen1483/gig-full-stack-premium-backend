package com.devskills.gigfullstackpremium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devskills.gigfullstackpremium.domain.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByTitle(String title);
	void deleteByTitle(String title);
}

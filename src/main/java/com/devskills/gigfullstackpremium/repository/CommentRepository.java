package com.devskills.gigfullstackpremium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
}

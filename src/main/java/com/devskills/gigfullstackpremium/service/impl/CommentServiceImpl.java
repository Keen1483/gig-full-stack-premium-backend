package com.devskills.gigfullstackpremium.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.exception.ResourceNotFoundException;
import com.devskills.gigfullstackpremium.repository.CommentRepository;
import com.devskills.gigfullstackpremium.service.CommentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepo;

	@Override
	public List<Comment> getComments() {
		log.info("Fetching all comments");
		return commentRepo.findAll();
	}

	@Override
	public Comment getComment(Long id) {
		log.info("Fetching comment {}", id);
		Optional<Comment> optionalComment = commentRepo.findById(id);
		
		if (optionalComment.isPresent()) {
			return optionalComment.get();
		} else {
			log.error("Comment {} not found in the database", id);
			throw new ResourceNotFoundException("Comment " + id + " not found in the database");
		}
	}

	@Override
	public Comment saveComment(Comment comment) {
		log.info("Saving new post {} in the database", comment.getId());
		comment.setCreatedAt(LocalDateTime.now());
		return commentRepo.save(comment);
	}

	@Override
	public Comment updateComment(Long id, Comment comment) {
		Optional<Comment> optionalComment = commentRepo.findById(id);
		
		if (optionalComment.isPresent()) {
			Comment currentComment = optionalComment.get();
			log.info("Updating post: {}", currentComment.getId());
			
			String content = comment.getContent();
			if (content != null && !content.equals(currentComment.getContent())) {
				currentComment.setContent(content);
			}
			
			currentComment.setEditedAt(LocalDateTime.now());
			
			return commentRepo.save(currentComment);
		} else {
			log.error("Comment {} not found in database", comment.getId());
			throw new ResourceNotFoundException("Comment " + comment.getId() + " not found in database");
		}
	}

	@Override
	public void deleteComment(Long id) {
		Optional<Comment> optionalComment = commentRepo.findById(id);
		
		if (optionalComment.isPresent()) {
			log.warn("Deleting comment {} in the database", id);
			commentRepo.deleteById(id);
		} else {
			log.error("Comment {} not found in the database", id);
			throw new ResourceNotFoundException("Comment " + id + " not found in the database");
		}

	}

}

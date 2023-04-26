package com.devskills.gigfullstackpremium.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.domain.Post;
import com.devskills.gigfullstackpremium.exception.ResourceNotFoundException;
import com.devskills.gigfullstackpremium.repository.CommentRepository;
import com.devskills.gigfullstackpremium.repository.PostRepository;
import com.devskills.gigfullstackpremium.service.PostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
	
	private final PostRepository postRepo;
	private final CommentRepository commentRepo;

	@Override
	public List<Post> getPosts() {
		log.info("Fetching all posts");
		return postRepo.findAll();
	}

	@Override
	public Post getPost(String title) {
		log.info("Fetching post {}", title);
		Optional<Post> optionalPost = postRepo.findByTitle(title);
		
		if (optionalPost.isPresent()) {
			return optionalPost.get();
		} else {
			log.error("Post {} not found in the database", title);
			throw new ResourceNotFoundException("Post " + title + " not found in the database");
		}
	}

	@Override
	public Post savePost(Post post) {
		log.info("Saving new post {} in the database", post.getTitle());
		post.setCreatedAt(LocalDateTime.now());
		return postRepo.save(post);
	}

	@Override
	public Post updatePost(Long id, Post post) {
		Optional<Post> optionalPost = postRepo.findById(id);
		
		if (optionalPost.isPresent()) {
			Post currentPost = optionalPost.get();
			log.info("Updating post: {}", currentPost.getTitle());
			
			String title = post.getTitle();
			if (title != null && !title.equals(currentPost.getTitle())) {
				currentPost.setTitle(title);
			}
			
			String content = post.getContent();
			if (content != null && !content.equals(currentPost.getContent())) {
				currentPost.setContent(content);
			}
			
			String imageUrl = post.getImageUrl();
			if (imageUrl != null && !imageUrl.equals(currentPost.getImageUrl())) {
				currentPost.setImageUrl(imageUrl);
			}
			
			currentPost.setEditedAt(LocalDateTime.now());
			
			return postRepo.save(currentPost);
		} else {
			log.error("Post {} not found in database", post.getTitle());
			throw new ResourceNotFoundException("Post " + post.getTitle() + " not found in database");
		}
	}

	@Override
	public void deletePost(String title) {
		Optional<Post> optionalPost = postRepo.findByTitle(title);
		
		if (optionalPost.isPresent()) {
			log.warn("Deleting post {} in the database", title);
			Post post = optionalPost.get();
			
			// Remove Comments
			post.getComments().removeAll(post.getComments());
			post = postRepo.save(post);
			
			// Finally, delete post
			postRepo.deleteByTitle(title);
		} else {
			log.error("Post {} not found in the database", title);
			throw new ResourceNotFoundException("Post " + title + " not found in the database");
		}

	}
	
	@Override
	public List<Comment> getComments(Post post) {
		return commentRepo.findByPost(post);
	}

}

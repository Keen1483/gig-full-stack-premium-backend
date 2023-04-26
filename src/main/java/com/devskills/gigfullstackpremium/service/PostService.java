package com.devskills.gigfullstackpremium.service;

import java.util.List;

import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.domain.Post;

public interface PostService {
	List<Post> getPosts();
	Post getPost(String title);
	Post savePost(Post post);
	Post updatePost(Long id, Post post);
	void deletePost(String title);
	
	List<Comment> getComments(Post post);
}

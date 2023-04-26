package com.devskills.gigfullstackpremium.service;

import java.util.List;

import com.devskills.gigfullstackpremium.domain.Comment;

public interface CommentService {
	List<Comment> getComments();
	Comment getComment(Long id);
	Comment saveComment(Comment comment);
	Comment updateComment(Long id, Comment comment);
	void deleteComment(Long id);
}

package com.devskills.gigfullstackpremium.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentResource {

	private final CommentService commentService;
	
	// GET METHODS
	@GetMapping("")
	public ResponseEntity< List<Comment> > getComments() {
		return ResponseEntity.ok().body(commentService.getComments());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(commentService.getComment(id));
	}
	
	
	// POST METHODS
	@PostMapping("/save")
	public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/comments/save").toUriString());
		return ResponseEntity.created(uri).body(commentService.saveComment(comment));
	}
	
	// POST METHODS
	@PutMapping("/edit/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
		return ResponseEntity.ok().body(commentService.updateComment(id, comment));
	}
	
	// DELETE METHODS
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
		commentService.deleteComment(id);
		return ResponseEntity.ok().build();
	}

}

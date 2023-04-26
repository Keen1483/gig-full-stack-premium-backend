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
import com.devskills.gigfullstackpremium.domain.Post;
import com.devskills.gigfullstackpremium.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostResource {

	private final PostService postService;
	
	// GET METHODS
	@GetMapping("")
	public ResponseEntity< List<Post> > getPosts() {
		return ResponseEntity.ok().body(postService.getPosts());
	}
	
	@GetMapping("/{title}")
	public ResponseEntity<Post> getPost(@PathVariable("title") String title) {
		return ResponseEntity.ok().body(postService.getPost(title));
	}
	
	
	// POST METHODS
	@PostMapping("/save")
	public ResponseEntity<Post> savePost(@RequestBody Post post) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/posts/save").toUriString());
		return ResponseEntity.created(uri).body(postService.savePost(post));
	}
	
	// POST METHODS
	@PutMapping("/edit/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
		return ResponseEntity.ok().body(postService.updatePost(id, post));
	}
	
	@PostMapping("/comments")
	public ResponseEntity< List<Comment> > getComments(@RequestBody Post post) {
		return ResponseEntity.ok().body(postService.getComments(post));
	}
	
	
	// DELETE METHODS
	@DeleteMapping("/delete/{title}")
	public ResponseEntity<?> deletePost(@PathVariable("title") String title) {
		postService.deletePost(title);
		return ResponseEntity.ok().build();
	}

}

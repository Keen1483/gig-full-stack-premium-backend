package com.devskills.gigfullstackpremium.domain;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy=AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	private String name;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@ManyToMany(fetch=EAGER)
	private Set<Role> roles = new HashSet<>();
	
	// Mapped Posts
	@JsonIgnore
	@OneToMany(mappedBy = "author",
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<>();
	
	// Mapped Comments
	@JsonIgnore
	@OneToMany(mappedBy = "author",
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<>();
	
	@Column(nullable=false)
	private LocalDateTime createdAt;
	
	private LocalDateTime editedAt;
	
	// Add and Remove role
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
	}
	
	// Add an Remove Post
	public void addPost(Post post) {
		posts.add(post);
	}
	
	public void removePost(Post post) {
		posts.remove(post);
	}
	
	// Add and Remove Comments
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
	}

}

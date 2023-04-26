package com.devskills.gigfullstackpremium.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String title;
	
	@Column(unique = true, nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User author;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post",
			orphanRemoval = true,
			fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();
	
	private LocalDateTime createdAt;
	private LocalDateTime editedAt;
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
	}

}

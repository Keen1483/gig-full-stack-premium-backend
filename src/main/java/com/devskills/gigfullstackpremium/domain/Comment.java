package com.devskills.gigfullstackpremium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Post post;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User author;
	
	private LocalDateTime createdAt;
	private LocalDateTime editedAt;

}

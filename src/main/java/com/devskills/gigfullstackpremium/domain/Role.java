package com.devskills.gigfullstackpremium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Comparable<Role> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable=false)
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Collection<User> users = new ArrayList<>();
	
	private LocalDateTime createdAt;
	private LocalDateTime editedAt;
	
	@Override
	public int compareTo(Role role) {
		return this.getName().compareTo(role.getName());
	}

}

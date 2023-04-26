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

import com.devskills.gigfullstackpremium.domain.Role;
import com.devskills.gigfullstackpremium.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleResource {

private final RoleService roleService;
	
	// GET METHODS
	@GetMapping("")
	public ResponseEntity< List<Role> > getRoles() {
		return ResponseEntity.ok().body(roleService.getRoles());
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Role> getRole(@PathVariable("name") String name) {
		return ResponseEntity.ok().body(roleService.getRole(name));
	}
	
	// POST METHODS
	@PostMapping("/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles/save").toUriString());
		return ResponseEntity.created(uri).body(roleService.saveRole(role));
	}
	
	// PUT METHODS
	@PutMapping("/edit/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable("id") Long id, @RequestBody Role role) {
		return ResponseEntity.ok().body(roleService.updateRole(id, role));
	}
	
	// DELETE METHODS
	@DeleteMapping("/delete/{name}")
	public ResponseEntity<?> deleteRole(@PathVariable("name") String name) {
		roleService.deleteRole(name);
		return ResponseEntity.ok().build();
	}

}

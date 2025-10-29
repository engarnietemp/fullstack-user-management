package com.enzog.backend.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enzog.backend.entity.User;
import com.enzog.backend.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	private final UserService userService;
	

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
	    return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		
		userService.deleteUser(userId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
	    
		User dbUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(dbUser);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId,@Valid @RequestBody User user) {
	    
		User dbUser = userService.updateUser(userId, user);
	    return ResponseEntity.ok(dbUser);
	}	
	
	@PatchMapping("/{userId}")
	public ResponseEntity<User> updateUserFields(@PathVariable Long userId, @RequestBody Map<String, Object> patchPayload) {
		
		User patchedUser = userService.patchUser(userId, patchPayload);		
		return ResponseEntity.ok(patchedUser);
	}
}

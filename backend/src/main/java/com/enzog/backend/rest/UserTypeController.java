package com.enzog.backend.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enzog.backend.entity.UserType;
import com.enzog.backend.service.UserTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user-types")
@CrossOrigin(origins = "http://localhost:4200")
public class UserTypeController {

	private final UserTypeService userTypeService;
	
	public UserTypeController(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserType>> getAllUserTypes() {
		return ResponseEntity.ok(userTypeService.getAllUserTypes());
	}
	
	@GetMapping("/{userTypeId}")
	public ResponseEntity<UserType> getUserTypeById(@PathVariable Long userTypeId) {
		return ResponseEntity.ok(userTypeService.getUserTypeById(userTypeId));
	}
	
	@DeleteMapping("/{userTypeId}")
	public ResponseEntity<Void> deleteUserType(@PathVariable Long userTypeId) {
		
		userTypeService.deleteUserType(userTypeId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<UserType> addUserType(@Valid @RequestBody UserType userType) {
		UserType dbUserType = userTypeService.createUserType(userType);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dbUserType);
	}
	
	@PutMapping("/{userTypeId}")
	public ResponseEntity<UserType> updateUserType(@PathVariable Long userTypeId,@Valid @RequestBody UserType userType) {
	    
		UserType dbUserType = userTypeService.updateUserType(userTypeId, userType);
	    return ResponseEntity.ok(dbUserType);
	}	
}
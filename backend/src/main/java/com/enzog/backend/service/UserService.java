package com.enzog.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enzog.backend.entity.User;
import com.enzog.backend.entity.UserType;
import com.enzog.backend.exception.DataConflictException;
import com.enzog.backend.exception.ResourceNotFoundException;
import com.enzog.backend.exception.ValidationException;
import com.enzog.backend.repository.UserRepository;
import com.enzog.backend.repository.UserTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final UserTypeRepository userTypeRepository;
	
	private final ObjectMapper objectMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.userTypeRepository = userTypeRepository;
		
		this.objectMapper = objectMapper;
	}
	
	
    /* ========== READ OPERATIONS ========== */
	
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", userId));
	}

    /* ========== CREATE OPERATION ========== */
	
	public User createUser(User user) {
		
		// ID must be null
		if(user.getId() != null) {
			throw new ValidationException("User ID must not be provided for creation");
		}
		
		// email must be unique
		if(userRepository.existsByEmail(user.getEmail())) {
			// We don't want to share if an email exists to users.
			throw new ValidationException("Unable to create user with provided data");
		}
		
		if(user.getUserType() == null || user.getUserType().getId() == null) {
			throw new ValidationException("UserType is required");
		}
        
        UserType userType = userTypeRepository.findById(user.getUserType().getId())
            .orElseThrow(() -> new ResourceNotFoundException("UserType", user.getUserType().getId()));
        
        user.setUserType(userType);			
		
		return userRepository.save(user);
	}
	
    /* ========== UPDATE OPERATION ========== */
	
	public User updateUser(Long userId, User user) {

	    if (!userRepository.existsById(userId)) {
	        throw new ResourceNotFoundException("User", userId);
	    }
	    
	    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
	    
	    if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
	        throw new DataConflictException("Email already in use by another user");
	    }
	    

	    if (user.getUserType() == null || user.getUserType().getId() == null) {
	        throw new ValidationException("UserType is required");
	    }
	    
	    UserType userType = userTypeRepository.findById(user.getUserType().getId())
	        .orElseThrow(() -> new ResourceNotFoundException("UserType", user.getUserType().getId()));
	    
	    user.setId(userId);
	    user.setUserType(userType);
	    
	    return userRepository.save(user);	
	}
	
	public User patchUser(Long userId, Map<String, Object> patchPayload) {
	    
		User existingUser = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", userId));
	    
		patchPayload.remove("id");
	    
		ObjectNode existingNode = objectMapper.convertValue(existingUser, ObjectNode.class);
		ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);
		
		existingNode.setAll(patchNode);
		
		User patchedUser = objectMapper.convertValue(existingNode, User.class);
		
		// Checking UserType post patch
		if(patchedUser.getUserType() != null && patchedUser.getUserType().getId() != null) {
			UserType userType = userTypeRepository.findById(patchedUser.getUserType().getId())
					.orElseThrow(() -> new ResourceNotFoundException("UserType", patchedUser.getUserType().getId()));
			
			patchedUser.setUserType(userType);
		}
		
		// Checking email is unique
		if(!existingUser.getEmail().equals(patchedUser.getEmail())) {
			if(userRepository.existsByEmail(patchedUser.getEmail())) {
				throw new DataConflictException("Email already exists in the database");
			}
		}
		
		return userRepository.save(patchedUser);
	}
	
    /* ========== DELETE OPERATION ========== */
	
	public void deleteUser(Long userId) {
		if(!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("User", userId);
		}
	    
		userRepository.deleteById(userId);
	}
	
}

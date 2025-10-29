package com.enzog.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.enzog.backend.entity.UserType;
import com.enzog.backend.exception.DataConflictException;
import com.enzog.backend.exception.ResourceNotFoundException;
import com.enzog.backend.exception.ValidationException;
import com.enzog.backend.repository.UserRepository;
import com.enzog.backend.repository.UserTypeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserTypeService {
	
	private final UserRepository userRepository;
	private final UserTypeRepository userTypeRepository;
	
	public UserTypeService(UserRepository userRepository, UserTypeRepository userTypeRepository) {
		this.userRepository = userRepository;
		this.userTypeRepository = userTypeRepository;
	}
	
    /* ========== READ OPERATIONS ========== */
	
	public List<UserType> getAllUserTypes() {
		return this.userTypeRepository.findAll();
	}
	
	public UserType getUserTypeById(Long userTypeId) {
		
		return userTypeRepository.findById(userTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("UserType", userTypeId));
	}
    
	/* ========== CREATE OPERATIONS ========== */
	
    public UserType createUserType(UserType userType) {
        
        // ID must be null
        if (userType.getId() != null) {
            throw new ValidationException("UserType ID must not be provided for creation");
        }
        
        // Label must be unique
        if (userTypeRepository.existsByLabel(userType.getLabel())) {
            throw new DataConflictException("UserType with label '" + userType.getLabel() + "' already exists");
        }
        
        return userTypeRepository.save(userType);
    }
    
	
    /* ========== UPDATE OPERATIONS ========== */
	
    public UserType updateUserType(Long userTypeId, UserType userType) {
        
		// UserType exists
		if (!userTypeRepository.existsById(userTypeId)) {
            throw new ResourceNotFoundException("UserType", userTypeId);
        }
		
		// Label must be unique
        Optional<UserType> existingType = userTypeRepository.findByLabel(userType.getLabel());
        
        if (existingType.isPresent() && !existingType.get().getId().equals(userTypeId)) {
            throw new DataConflictException("Label already in use by another UserType");
        }
        
        userType.setId(userTypeId);
        
        return userTypeRepository.save(userType);
	}
    
    /* No need for a Patch update since UserType has only one attribute*/
    
    /* ========== DELETE OPERATIONS ========== */  
    
    public void deleteUserType(Long userTypeId) {
		
    	// UserType exists
		if (!userTypeRepository.existsById(userTypeId)) {
            throw new ResourceNotFoundException("UserType", userTypeId);
        }
		
		long usersCount = userRepository.countByUserTypeId(userTypeId);
		
		if(usersCount > 0) {
			throw new DataConflictException(
					"Cannot delete UserType: " + usersCount + " user(s) still assigned to this type"
					);
		}
		userTypeRepository.deleteById(userTypeId);
    }
}

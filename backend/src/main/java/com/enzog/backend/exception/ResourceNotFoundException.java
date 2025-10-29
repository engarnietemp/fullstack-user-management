package com.enzog.backend.exception;

public class ResourceNotFoundException extends BusinessException {
	
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resource, Long id) {
		super(resource + " not found with ID : " + id);
	}
}

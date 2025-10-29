package com.enzog.backend.exception;

public class DataConflictException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DataConflictException(String message) {
		super(message);
	}
}

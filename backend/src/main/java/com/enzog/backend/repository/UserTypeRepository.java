package com.enzog.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enzog.backend.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType,Long> {
	boolean existsByLabel(String label);
	Optional<UserType> findByLabel(String label);
}

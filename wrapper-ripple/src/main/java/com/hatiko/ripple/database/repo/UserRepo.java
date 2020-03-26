package com.hatiko.ripple.database.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hatiko.ripple.database.model.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
	
	UserEntity findOneByUsername(String username);
	
	List<UserEntity> findAllByUsername(String username);
	
}

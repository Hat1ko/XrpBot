package com.hatiko.ripple.database.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hatiko.ripple.database.dto.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
	
	UserEntity findOneByUsername(String username);
	
	List<UserEntity> findAllLikeUsername(String username);
}

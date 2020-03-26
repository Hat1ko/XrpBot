package com.hatiko.ripple.database.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatiko.ripple.database.model.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
	
	UserEntity findOneByUsername(String username);
	
	List<UserEntity> findAllByUsername(String username);
	
}

package com.hatiko.ripple.database.repo;

import org.springframework.data.repository.CrudRepository;

import com.hatiko.ripple.database.dto.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

}

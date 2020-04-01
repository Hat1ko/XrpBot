package com.hatiko.ripple.telegram.bot.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatiko.ripple.telegram.bot.database.model.MessageIdEntity;

@Repository
public interface MessageIdRepository extends JpaRepository<MessageIdEntity, Integer> {
	
}

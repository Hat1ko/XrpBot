package com.hatiko.ripple.telegram.bot.database.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatiko.ripple.telegram.bot.database.model.AccountLastIdEntity;

@Repository
public interface AccountLastIdRepository extends JpaRepository<AccountLastIdEntity, Long> {
	AccountLastIdEntity findOneByPublicKey(String publicKey);
	List<AccountLastIdEntity> findAllByPublicKey(String publicKey);
}

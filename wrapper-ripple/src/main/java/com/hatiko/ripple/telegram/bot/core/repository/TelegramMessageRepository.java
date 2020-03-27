package com.hatiko.ripple.telegram.bot.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hatiko.ripple.telegram.bot.core.model.TelegramMessage;

//@RepositoryRestResource(collectionResourceRel = "telegram_message", path = "messages")
public interface TelegramMessageRepository extends PagingAndSortingRepository<TelegramMessage, Integer> {

}

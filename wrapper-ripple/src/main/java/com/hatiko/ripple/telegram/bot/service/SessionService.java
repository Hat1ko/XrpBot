package com.hatiko.ripple.telegram.bot.service;

import com.hatiko.ripple.telegram.bot.dto.session.ChatSession;

public interface SessionService {
	
	void createSession(Long chatId, String username);
	ChatSession getSession(Long chatId);
	void deleteSession(Long chatId);
	void logOutSessions();//scheduled
}

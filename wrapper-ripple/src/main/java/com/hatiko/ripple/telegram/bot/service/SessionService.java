package com.hatiko.ripple.telegram.bot.service;

import com.hatiko.ripple.telegram.bot.dto.session.ChatSession;

public interface SessionService {
	
	Boolean createSession(Long chatId, String username, String password);
	ChatSession getSession(Long chatId);
	Boolean checkSessionExist(Long chatId);
	void deleteSession(Long chatId);
	void logOutSessions();//scheduled
}

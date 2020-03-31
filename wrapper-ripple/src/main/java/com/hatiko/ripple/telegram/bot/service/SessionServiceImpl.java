package com.hatiko.ripple.telegram.bot.service;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.dto.session.ChatSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SessionServiceImpl implements SessionService {
	
	List<ChatSession> sessions = new ArrayList<>();
	
	@Override
	public void createSession(Long chatId, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatSession getSession(Long chatId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSession(Long chatId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOutSessions() {
		// TODO Auto-generated method stub
		
	}
}

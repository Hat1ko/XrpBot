package com.hatiko.ripple.telegram.bot.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.session.ChatSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SessionServiceImpl implements SessionService {

	private final XrpDatabaseOperator databaseOperator;
	private final ResponseMessageOperator responseMessageOperator;
	private List<ChatSession> sessions = new ArrayList<>();

	@Override
	public Boolean createSession(Long chatId, String username, String password) {

		if(!databaseOperator.checkLogIn(username, password)) {
			return Boolean.FALSE;
		}
		
		if(sessions.stream().filter(s -> s.getChatId().equals(chatId)).findAny().isPresent()) {
			return Boolean.FALSE;
		}
		
		UserDTO newUser = databaseOperator.getUserByUsername(username);
		ChatSession session = ChatSession.builder().chatId(chatId).username(username).publicKey(newUser.getPublicKey())
				.privateKey(newUser.getPrivateKey()).creationTime(LocalDateTime.now()).build();
		
		sessions.add(session);
		
		return Boolean.TRUE;
	}

	@Override
	public ChatSession getSession(Long chatId) {
		return sessions.parallelStream().filter(s -> s.getChatId().equals(chatId)).findAny().get();
	}

	@Override
	public void deleteSession(Long chatId) {
		sessions.removeIf(e -> e.getChatId().equals(chatId));
	}

	@Override
	@Scheduled(cron = "${telegram.bot.session.cron}")
	public void logOutSessions() {

//		sessions.removeIf(e -> ChronoUnit.MINUTES.between(e.getCreationTime(), LocalDateTime.now()) >= 15L);
		sessions.stream().filter(e -> ChronoUnit.MINUTES.between(e.getCreationTime(), LocalDateTime.now()) >= 15L).forEach(s -> {
			deleteSession(s.getChatId());
			Integer messageId = responseMessageOperator.responseLogOut(s.getChatId());
			//TODO: add message
		});
	}
}

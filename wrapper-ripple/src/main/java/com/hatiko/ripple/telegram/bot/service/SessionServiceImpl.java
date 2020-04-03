package com.hatiko.ripple.telegram.bot.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private final MessageDeletionService messageDeletionService;
	private final LongTermOperationService operationService;
	private List<ChatSession> sessions = new ArrayList<>();

	@Override
	public Boolean createSession(Long chatId, String username, String password) {

		log.info("Creating session | chatId : {}, username : {}", chatId, username);
		
		if (!databaseOperator.checkLogIn(username, password)) {
			return Boolean.FALSE;
		}

		if (sessions.stream().filter(s -> s.getChatId().equals(chatId)).findAny().isPresent()) {
			return Boolean.FALSE;
		}

		UserDTO newUser = databaseOperator.getUserByUsername(username);
		ChatSession session = ChatSession.builder().chatId(chatId).username(username).publicKey(newUser.getPublicKey())
				.privateKey(newUser.getPrivateKey()).creationTime(LocalDateTime.now()).build();

		sessions.add(session);

		return Boolean.TRUE;
	}

	@Override
	public Optional<ChatSession> getSession(Long chatId) {
		
		log.info("Getting optional sesion by chatId = {}", chatId);
		return sessions.parallelStream().filter(s -> s.getChatId().equals(chatId)).findAny();
	}

	@Override
	public Boolean checkSessionExist(Long chatId) {
		
		log.info("Checking if session exists by chatId = {}", chatId);
		return Optional.ofNullable(getSession(chatId)).isPresent();
	}

	@Override
	public void deleteSession(Long chatId) {
		
		log.info("Deleting session by chatId = {}", chatId);
		sessions.removeIf(e -> e.getChatId().equals(chatId));
		messageDeletionService.deleteMessages(chatId);
	}

	@Override
	@Scheduled(cron = "${telegram.bot.session.cron}")
	public void logOutSessions() {

		log.info("Logging sessions out");
		sessions.stream().filter(e -> ChronoUnit.MINUTES.between(e.getCreationTime(), LocalDateTime.now()) >= 10L)
				.forEach(s -> {
					deleteSession(s.getChatId());
					operationService.removeOperation(s.getChatId());
					Integer messageId = responseMessageOperator.responseLogOut(s.getChatId());
					databaseOperator.updateMessageId(s.getChatId(), messageId, null);
				});
	}

}

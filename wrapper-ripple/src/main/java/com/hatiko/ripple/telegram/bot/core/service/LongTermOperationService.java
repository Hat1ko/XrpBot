package com.hatiko.ripple.telegram.bot.core.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.dto.OperationDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class LongTermOperationService {

	private List<OperationDTO> operations;

	public void addOpearion(Integer chatId, Integer messageId, String operation, Method method, Integer argc) {

		OperationDTO operationDTO = OperationDTO.builder().chatId(chatId).messageId(messageId).method(method)
				.operation(operation).argc(argc).build();

		operations = operations.parallelStream().filter(oper -> oper.getChatId().equals(chatId))
				.collect(Collectors.toList());

		operations.add(operationDTO);
	}
}

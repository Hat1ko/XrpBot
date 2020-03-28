package com.hatiko.ripple.telegram.bot.core.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

	private List<OperationDTO> operations = new ArrayList<>();

	public void addOpearion(Long chatId, Integer messageId, String operation, Object operator, Method method,
			Integer argc) {

		OperationDTO operationDTO = OperationDTO.builder().chatId((int) (long) chatId).messageId(messageId)
				.operator(operator).method(method).operation(operation).argc(argc).params(new ArrayList<>()).build();

		operations = operations.stream().filter(oper -> oper.getChatId().equals(chatId)).collect(Collectors.toList());

		operations.add(operationDTO);
	}

	public Object insertArgument(String argv, Integer chatId) {

		return operations.parallelStream().filter(oper -> chatId.equals(oper.getChatId()))
				.map(oper -> {
			oper.getParams().add(argv);
			if (oper.getArgc().equals(oper.getParams().size())) {
				try {
					return oper.getMethod().invoke(oper.getOperator(), oper.getParams().toArray());
				} catch (IllegalAccessException e) {
					log.error(e.getMessage());
					return null;
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage());
					return null;
				} catch (InvocationTargetException e) {
					log.error(e.getMessage());
					return null;
				}
			}
			return oper.getOperation();
		}).findAny().get();
	}
}

package com.hatiko.ripple.telegram.bot.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.dto.telegram.OperationDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class LongTermOperationService {

	private List<OperationDTO> operations = new ArrayList<>();

	public void removeOperation(Long chatId) {
		operations.removeIf(oper -> oper.getChatId().equals((int)(long) chatId));
	}

	public void addOpearion(Long chatId, Integer messageId, String operation, Object operator, Method method,
			Integer argc) {

		OperationDTO operationDTO = OperationDTO.builder().chatId((int) (long) chatId).messageId(messageId)
				.operator(operator).method(method).operation(operation).argc(argc).params(new ArrayList<>()).build();

		removeOperation(chatId);

		operations.add(operationDTO);
	}

	public String getMethodName(Long chatId) {
		return operations.parallelStream().filter(oper -> oper.getChatId().equals((int) (long) chatId)).findAny().get()
				.getOperation();
	}

	public Object insertArgument(Object argv, Long chatId) {

		return operations.parallelStream().filter(oper -> chatId.equals((long) oper.getChatId())).map(oper -> {
			oper.getParams().add(argv);
			if (oper.getArgc().equals(oper.getParams().size())) {
				try {
					removeOperation(chatId);
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
			if (oper.getArgc() < oper.getParams().size()) {
				removeOperation((long) (int) oper.getChatId());
				return null;
			}
			return oper.getParams().size();
		}).findAny().get();
	}
}

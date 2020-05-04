package com.hatiko.ripple.telegram.bot.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

		log.info("Removing operation by chatId = {}", chatId);
		operations.removeIf(oper -> oper.getChatId().equals((int) (long) chatId));
	}

	public void addOperation(Long chatId, Integer messageId, String operation, Object operator, Method method,
							 Integer argc) {

		removeOperation(chatId);

		log.info("Adding operation | chatId : {}, messageId : {}, operation : {}, numOArgs : {}", chatId, messageId,
				operation, argc);
		OperationDTO operationDTO = OperationDTO.builder().chatId((int) (long) chatId).messageId(messageId)
				.operator(operator).method(method).operation(operation).argc(argc).params(new ArrayList<>()).build();

		operations.add(operationDTO);
	}

	public String getMethodName(Long chatId) {
		
		log.info("Getting method name for chatId = {}", chatId);
		return operations.parallelStream().filter(oper -> oper.getChatId().equals((int) (long) chatId)).findAny()
				.orElseGet(OperationDTO::new).getOperation();
	}

	public Object insertArgument(Object argv, Long chatId) {

		log.info("Inserting argument by chatId = {}", chatId);
		return operations.parallelStream().filter(oper -> chatId.equals((long) oper.getChatId())).map(oper -> {
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
			if (oper.getArgc() < oper.getParams().size()) {
				return null;
			}
			return oper.getParams().size();
		}).findAny().orElseGet(Object::new);
	}

	public OperationDTO getOperation(Long chatId) {
		
		log.info("Get operation by chatId = {}", chatId);
		return operations.stream().filter(oper -> oper.getChatId().equals((int) (long) chatId)).findAny().get();
	}
}

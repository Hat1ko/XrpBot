package com.hatiko.ripple.telegram.bot.core.dto;

import java.lang.reflect.Method;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {
	
	private Integer chatId;
	private Integer messageId;
	private String operation;
	private Method method;
	private List<Object> params;
	private Integer argc;
}

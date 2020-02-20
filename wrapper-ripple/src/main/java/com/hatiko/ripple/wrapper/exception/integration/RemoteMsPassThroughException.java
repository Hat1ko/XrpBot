package com.hatiko.ripple.wrapper.exception.integration;

import org.springframework.util.StringUtils;

import com.hatiko.ripple.wrapper.dto.ErrorInfoDto;

public class RemoteMsPassThroughException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ErrorInfoDto errorInfoDto;

    RemoteMsPassThroughException(ErrorInfoDto errorInfoDto) {
        super(StringUtils.isEmpty(errorInfoDto.getMessages()) ? "Unknown remote MS error." : errorInfoDto.getMessages().get(0));
        this.errorInfoDto = errorInfoDto;
    }

    public ErrorInfoDto getErrorInfoDto() {
        return errorInfoDto;
    }
}

package io.cryptex.ms.wrapper.ripple.exception.integration;

import io.cryptex.ms.wrapper.ripple.dto.ErrorInfoDto;
import org.springframework.util.StringUtils;

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

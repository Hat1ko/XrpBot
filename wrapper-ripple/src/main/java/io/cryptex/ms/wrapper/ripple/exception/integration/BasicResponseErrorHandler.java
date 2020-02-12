package io.cryptex.ms.wrapper.ripple.exception.integration;

import io.cryptex.ms.wrapper.ripple.dto.ErrorInfoDto;
import io.cryptex.ms.wrapper.ripple.exception.InnerServiceException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class BasicResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final String PROVIDER_ERROR_MESSAGE_TEMPLATE = "Server error [%s]: %s";

    abstract String getServiceName();

    abstract void handleError(ErrorInfoDto errorInfoDto);

    private MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        Optional<ErrorInfoDto> errorInfoDtoOptional = fetchErrorInfoDto(response);
        errorInfoDtoOptional.ifPresent(errorInfoDto -> {
            if (!CollectionUtils.isEmpty(errorInfoDto.getMessages())) {
                handleError(errorInfoDto);
            }
        });

        throwProviderException(response, statusCode);
    }

    private void throwProviderException(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String errorMsg = getErrorMessage(response, statusCode);
        throw new InnerServiceException(String.format(PROVIDER_ERROR_MESSAGE_TEMPLATE, getServiceName(), errorMsg));
    }

    private String getErrorMessage(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        return statusCode + " " + IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
    }

    private Optional<ErrorInfoDto> fetchErrorInfoDto(ClientHttpResponse response) {
        try {
            return Optional.of((ErrorInfoDto) converter.read(ErrorInfoDto.class, response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

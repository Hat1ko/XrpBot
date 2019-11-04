package io.cryptex.ms.wrapper.ripple.exception;

import io.cryptex.ms.wrapper.ripple.dto.ErrorInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerExceptionHandlerTest {

    @InjectMocks
    private GlobalControllerExceptionHandler objectUnderTest;

    @Test
    public void shouldHandleInnerServiceException() {
        InnerServiceException exception = new InnerServiceException("test message");
        ResponseEntity<ErrorInfoDto> entity = objectUnderTest.handleBaseException(exception, new MockHttpServletRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        assertEquals(ExceptionCode.INTERNAL_SERVICE_EXCEPTION.getDebugMessage(), entity.getBody().getMessages().get(0));
        assertEquals(ExceptionCode.INTERNAL_SERVICE_EXCEPTION.getCode(), entity.getBody().getCode().intValue());
    }

    @Test
    public void shouldHandleRuntimeException() {
        IllegalArgumentException exception = new IllegalArgumentException("parameter is not valid");
        ResponseEntity<ErrorInfoDto> entity = objectUnderTest.handleRuntimeException(exception, new MockHttpServletRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        assertEquals(ExceptionCode.UNKNOWN_EXCEPTION.getDebugMessage(), entity.getBody().getMessages().get(0));
        assertEquals(ExceptionCode.UNKNOWN_EXCEPTION.getCode(), entity.getBody().getCode().intValue());
    }
}

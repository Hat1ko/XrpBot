package io.cryptex.ms.wrapper.ripple.exception;


public class InnerServiceException extends BaseException {

	private static final long serialVersionUID = 7196443447873155998L;

	public InnerServiceException(String message, Throwable throwable) {
        super(ExceptionCode.INTERNAL_SERVICE_EXCEPTION, message, throwable);
    }

    public InnerServiceException(String message) {
        super(ExceptionCode.INTERNAL_SERVICE_EXCEPTION, message);
    }
}

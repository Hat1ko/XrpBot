package io.cryptex.ms.wrapper.ripple.exception;


public class NoSuchValueException extends BaseException {

	private static final long serialVersionUID = -3160431985138632859L;

	public NoSuchValueException(String message, Throwable throwable) {
        super(ExceptionCode.NO_SUCH_VALUE_EXCEPTION, message, throwable);
    }

    public NoSuchValueException(String message) {
        super(ExceptionCode.NO_SUCH_VALUE_EXCEPTION, message);
    }
}

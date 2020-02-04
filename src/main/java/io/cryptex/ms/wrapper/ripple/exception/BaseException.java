package io.cryptex.ms.wrapper.ripple.exception;


import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = -3751261242396036549L;

	private final ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDebugMessage());
        this.exceptionCode = exceptionCode;
    }

    public BaseException(ExceptionCode exceptionCode, String message , Throwable throwable) {
        super(message, throwable);
        this.exceptionCode = exceptionCode;
    }

    public BaseException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public final String getMessage() {
        return super.getMessage();
    }
}

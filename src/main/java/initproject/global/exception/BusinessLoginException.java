package initproject.global.exception;

import lombok.Getter;

public class BusinessLoginException extends RuntimeException{

    @Getter
    private final ExceptionCode exceptionCode;

    public BusinessLoginException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}

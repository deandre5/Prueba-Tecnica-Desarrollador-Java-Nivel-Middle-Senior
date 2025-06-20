package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import lombok.Getter;

@Getter

public class BusinessException extends RuntimeException{
    private final BusinessErrorMessage errorMessage;

    public BusinessException(BusinessErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}

package co.com.bancolombia.exception.technical;

import co.com.bancolombia.exception.technical.message.UnhandledErrorMessage;
import lombok.Getter;

@Getter

public class UnhandledException extends RuntimeException{
    private final transient UnhandledErrorMessage unhandledErrorMessage;

    public UnhandledException(Throwable cause, UnhandledErrorMessage unhandledErrorMessage) {
        super(unhandledErrorMessage.getMessage(), cause);
        this.unhandledErrorMessage = unhandledErrorMessage;
    }
}

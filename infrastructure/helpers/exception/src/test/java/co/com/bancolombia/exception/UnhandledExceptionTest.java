package co.com.bancolombia.exception;

import co.com.bancolombia.exception.technical.UnhandledException;
import co.com.bancolombia.exception.technical.message.UnhandledErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnhandledExceptionTest {
    @Test
    void shouldCreateUnhandledException() {
        Throwable cause = mock(Throwable.class);
        UnhandledErrorMessage unhandledErrorMessage = mock(UnhandledErrorMessage.class);
        when(unhandledErrorMessage.getMessage()).thenReturn("Test message");

        UnhandledException unhandledException = new UnhandledException(cause, unhandledErrorMessage);

        assertEquals("Test message", unhandledException.getMessage());
        assertEquals(unhandledErrorMessage, unhandledException.getUnhandledErrorMessage());
        assertEquals(cause, unhandledException.getCause());
    }
}

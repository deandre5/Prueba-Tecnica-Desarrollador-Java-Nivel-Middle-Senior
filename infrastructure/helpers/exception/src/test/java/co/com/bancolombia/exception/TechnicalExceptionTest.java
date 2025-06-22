package co.com.bancolombia.exception;

import co.com.bancolombia.exception.technical.TechnicalException;
import co.com.bancolombia.exception.technical.message.TechnicalErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TechnicalExceptionTest {
    @Test
    void shouldCreateTechnicalException() {
        Throwable cause = mock(Throwable.class);
        TechnicalErrorMessage technicalErrorMessage = mock(TechnicalErrorMessage.class);
        when(technicalErrorMessage.getMessage()).thenReturn("Test message");

        TechnicalException technicalException = new TechnicalException(cause, technicalErrorMessage);

        assertEquals("Test message", technicalException.getMessage());
        assertEquals(technicalErrorMessage, technicalException.getTechnicalErrorMessage());
        assertEquals(cause, technicalException.getCause());
    }

    @Test
    void shouldCreateTechnicalMessage() {
        Throwable cause = mock(Throwable.class);
        TechnicalErrorMessage technicalErrorMessage = mock(TechnicalErrorMessage.class);
        when(technicalErrorMessage.getMessage()).thenReturn("Test message");

        TechnicalException technicalException = new TechnicalException(technicalErrorMessage);

        assertEquals("Test message", technicalException.getMessage());
        assertEquals(technicalErrorMessage, technicalException.getTechnicalErrorMessage());
    }
}

package co.com.bancolombia.api.utils;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void validateRequired_ShouldThrowException_WhenValueIsNull() {
        Object nullValue = null;
        BusinessErrorMessage errorMessage = BusinessErrorMessage.DEFAULT_BAD_REQUEST;

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                Validator.validateRequired(nullValue, errorMessage)
        );

        assertEquals(errorMessage, thrown.getErrorMessage());
    }

    @Test
    void validateRequired_ShouldThrowException_WhenValueIsEmptyString() {
        String emptyString = "";
        BusinessErrorMessage errorMessage = BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_HASH;

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                Validator.validateRequired(emptyString, errorMessage)
        );

        assertEquals(errorMessage, thrown.getErrorMessage());
    }

    @Test
    void validateRequired_ShouldThrowException_WhenValueIsBlankString() {
        String blankString = "   ";
        BusinessErrorMessage errorMessage = BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CLAIM;

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                Validator.validateRequired(blankString, errorMessage)
        );

        assertEquals(errorMessage, thrown.getErrorMessage());
    }

    @Test
    void validateRequired_ShouldNotThrowException_WhenValueIsNotNullOrEmpty() {
        String validValue = "someValue";
        BusinessErrorMessage errorMessage = BusinessErrorMessage.DEFAULT_BAD_REQUEST;

        assertDoesNotThrow(() ->
                Validator.validateRequired(validValue, errorMessage)
        );
    }

    @Test
    void validateRequired_ShouldNotThrowException_WhenValueIsZero() {
        Integer zeroValue = 0;
        BusinessErrorMessage errorMessage = BusinessErrorMessage.DEFAULT_BAD_REQUEST;

        assertDoesNotThrow(() ->
                Validator.validateRequired(zeroValue, errorMessage)
        );
    }
}

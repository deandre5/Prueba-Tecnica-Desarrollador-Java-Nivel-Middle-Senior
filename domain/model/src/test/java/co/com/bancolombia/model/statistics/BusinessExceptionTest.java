package co.com.bancolombia.model.statistics;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionTest {
    @Test
    void testBusinessException() {
        BusinessErrorMessage businessErrorMessage = BusinessErrorMessage.PRECONDITION_FAILED;
        BusinessException businessException = new BusinessException(businessErrorMessage);

        assertEquals(businessErrorMessage, businessException.getErrorMessage());
        assertEquals(businessErrorMessage.getMessage(), businessException.getMessage());
    }
}

package co.com.bancolombia.api.utils;

import co.com.bancolombia.api.statistics.StatisticsResponseDataDTO;
import co.com.bancolombia.model.statistics.Statistics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class ResponseFactoryTest {
    @Test
    void getResponse_ShouldMapTimeStampCorrectly() {
        Statistics mockStatistics = mock(Statistics.class);
        String testTimeStamp = "2025-06-21T18:30:00Z"; // Un timestamp de ejemplo

        when(mockStatistics.getTimeStamp()).thenReturn(testTimeStamp);

        StatisticsResponseDataDTO resultResponse = ResponseFactory.getResponse(mockStatistics);

        assertNotNull(resultResponse, "El objeto StatisticsResponseDataDTO no debe ser nulo");
        assertNotNull(resultResponse.getData(), "El campo data no debe ser nulo");
        assertNotNull(resultResponse.getData().getStatistics(), "El campo statistics dentro de data no debe ser nulo");

        assertEquals("El timestamp no coincide", testTimeStamp, resultResponse.getData().getStatistics().getTimeStamp());

        Mockito.verify(mockStatistics, Mockito.times(1)).getTimeStamp();
    }
}

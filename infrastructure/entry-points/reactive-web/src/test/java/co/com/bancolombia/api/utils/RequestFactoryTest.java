package co.com.bancolombia.api.utils;

import co.com.bancolombia.api.statistics.StatisticsDTO;
import co.com.bancolombia.api.statistics.StatisticsRequestDataDTO;
import co.com.bancolombia.model.statistics.Statistics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class RequestFactoryTest {
    @Test
    void getRequest_ShouldMapAllFieldsCorrectly() {

        StatisticsRequestDataDTO mockRequestDataDTO = mock(StatisticsRequestDataDTO.class);
        StatisticsRequestDataDTO.StatisticsDataDTO mockStatisticsDataDTO = mock(StatisticsRequestDataDTO.StatisticsDataDTO.class);
        StatisticsDTO mockStatisticsDTO = mock(StatisticsDTO.class);

        when(mockRequestDataDTO.getData()).thenReturn(mockStatisticsDataDTO);
        when(mockStatisticsDataDTO.getStatistics()).thenReturn(mockStatisticsDTO);

        String testHash = "testHash123";
        Integer testMotivoCambio = 10;
        Integer testMotivoCompra = 20;
        Integer testMotivoDuda = 30;
        Integer testMotivoFelicitaciones = 40;
        Integer testMotivoGarantia = 50;
        Integer testMotivoReclamo = 60;
        Integer testTotalContactoClientes = 70;

        when(mockStatisticsDTO.getHash()).thenReturn(testHash);
        when(mockStatisticsDTO.getMotivoCambio()).thenReturn(testMotivoCambio);
        when(mockStatisticsDTO.getMotivoCompra()).thenReturn(testMotivoCompra);
        when(mockStatisticsDTO.getMotivoDuda()).thenReturn(testMotivoDuda);
        when(mockStatisticsDTO.getMotivoFelicitaciones()).thenReturn(testMotivoFelicitaciones);
        when(mockStatisticsDTO.getMotivoGarantia()).thenReturn(testMotivoGarantia);
        when(mockStatisticsDTO.getMotivoReclamo()).thenReturn(testMotivoReclamo);
        when(mockStatisticsDTO.getTotalContactoClientes()).thenReturn(testTotalContactoClientes);

        Statistics resultStatistics = RequestFactory.getRequest(mockRequestDataDTO);

        assertNotNull(resultStatistics, "El objeto Statistics no debe ser nulo");
        assertEquals("El hash no coincide", testHash, resultStatistics.getHash());
        assertEquals("El motivo de cambio no coincide", testMotivoCambio, resultStatistics.getMotivoCambio());
        assertEquals("El motivo de compra no coincide", testMotivoCompra, resultStatistics.getMotivoCompra());
        assertEquals("El motivo de duda no coincide", testMotivoDuda, resultStatistics.getMotivoDuda());
        assertEquals("El motivo de felicitaciones no coincide", testMotivoFelicitaciones, resultStatistics.getMotivoFelicitaciones());
        assertEquals("El motivo de garantía no coincide", testMotivoGarantia, resultStatistics.getMotivoGarantia());
        assertEquals("El motivo de reclamo no coincide", testMotivoReclamo, resultStatistics.getMotivoReclamo());
        assertEquals("El total de contacto de clientes no coincide", testTotalContactoClientes, resultStatistics.getTotalContactoClientes());

        Mockito.verify(mockRequestDataDTO, Mockito.times(8)).getData();
        Mockito.verify(mockStatisticsDataDTO, Mockito.times(8)).getStatistics();

        Mockito.verify(mockStatisticsDTO).getHash();
        Mockito.verify(mockStatisticsDTO).getMotivoCambio();
        Mockito.verify(mockStatisticsDTO).getMotivoCompra();
        Mockito.verify(mockStatisticsDTO).getMotivoDuda();
        Mockito.verify(mockStatisticsDTO).getMotivoFelicitaciones();
        Mockito.verify(mockStatisticsDTO).getMotivoGarantia();
        Mockito.verify(mockStatisticsDTO).getMotivoReclamo();
        Mockito.verify(mockStatisticsDTO).getTotalContactoClientes();
    }

}

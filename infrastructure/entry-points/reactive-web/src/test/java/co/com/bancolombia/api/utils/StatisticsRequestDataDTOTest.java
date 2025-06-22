package co.com.bancolombia.api.utils;

import co.com.bancolombia.api.statistics.StatisticsDTO;
import co.com.bancolombia.api.statistics.StatisticsRequestDataDTO;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsRequestDataDTOTest {

    private RequestFactory requestFactory;

    private StatisticsRequestDataDTO createValidDTO() {
        return StatisticsRequestDataDTO.builder()
                .data(StatisticsRequestDataDTO.StatisticsDataDTO.builder()
                        .statistics(StatisticsDTO.builder()
                                .totalContactoClientes(10)
                                .motivoReclamo(1)
                                .motivoGarantia(2)
                                .motivoDuda(3)
                                .motivoCompra(4)
                                .motivoFelicitaciones(5)
                                .motivoCambio(6)
                                .hash("validHash123")
                                .build())
                        .build())
                .build();
    }

    @Test
    void validate_ShouldNotThrowException_WhenAllFieldsAreValid() {
        StatisticsRequestDataDTO dto = createValidDTO();

        assertDoesNotThrow(dto::validate);
    }

    @Test
    void validate_ShouldThrowException_WhenDataIsNull() {
        StatisticsRequestDataDTO dto = StatisticsRequestDataDTO.builder()
                .data(null)
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenStatisticsIsNull() {
        StatisticsRequestDataDTO dto = StatisticsRequestDataDTO.builder()
                .data(StatisticsRequestDataDTO.StatisticsDataDTO.builder()
                        .statistics(null)
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenTotalContactoClientesIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .totalContactoClientes(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_TOTAL_CLIENTS, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoReclamoIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoReclamo(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CLAIM, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoGarantiaIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoGarantia(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_WARRANTY, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoDudaIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoDuda(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_DOUBT, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoCompraIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoCompra(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_BUY, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoFelicitacionesIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoFelicitaciones(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CONGRATS, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenMotivoCambioIsNull() {
        // Arrange
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .motivoCambio(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CHANGE, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenHashIsNull() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .hash(null)
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_HASH, thrown.getErrorMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenHashIsEmpty() {
        StatisticsRequestDataDTO dto = createValidDTO().toBuilder()
                .data(createValidDTO().getData().toBuilder()
                        .statistics(createValidDTO().getData().getStatistics().toBuilder()
                                .hash("")
                                .build())
                        .build())
                .build();

        BusinessException thrown = assertThrows(BusinessException.class, dto::validate);
        assertEquals(BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_HASH, thrown.getErrorMessage());
    }
}

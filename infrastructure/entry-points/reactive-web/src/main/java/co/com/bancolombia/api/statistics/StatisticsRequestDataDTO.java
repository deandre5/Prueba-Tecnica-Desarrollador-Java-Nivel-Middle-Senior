package co.com.bancolombia.api.statistics;

import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static co.com.bancolombia.api.utils.Validator.validateRequired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsRequestDataDTO {
    @NotNull(message = "%DEFAULT_BAD_REQUEST%")
    @Valid
    private StatisticsDataDTO data;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class StatisticsDataDTO{
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        @Valid
        private StatisticsDTO statistics;
    }

    public StatisticsRequestDataDTO validate(){
        validateRequired(data,BusinessErrorMessage.DEFAULT_BAD_REQUEST);
        validateRequired(data.getStatistics(), BusinessErrorMessage.DEFAULT_BAD_REQUEST);
        validateRequired(data.getStatistics().getTotalContactoClientes(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_TOTAL_CLIENTS);
        validateRequired(data.getStatistics().getMotivoReclamo(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CLAIM);
        validateRequired(data.getStatistics().getMotivoGarantia(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_WARRANTY);
        validateRequired(data.getStatistics().getMotivoDuda(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_DOUBT);
        validateRequired(data.getStatistics().getMotivoCompra(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_BUY);
        validateRequired(data.getStatistics().getMotivoFelicitaciones(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CONGRATS);
        validateRequired(data.getStatistics().getMotivoCambio(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_CHANGE);
        validateRequired(data.getStatistics().getHash(), BusinessErrorMessage.DEFAULT_BAD_REQUEST_STATISTICS_HASH);
        return this;
    }
}

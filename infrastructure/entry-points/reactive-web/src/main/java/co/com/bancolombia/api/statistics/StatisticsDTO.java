package co.com.bancolombia.api.statistics;

import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static co.com.bancolombia.api.utils.Validator.validateRequired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsDTO {

        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer totalContactoClientes;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoReclamo;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoGarantia;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoDuda;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoCompra;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoFelicitaciones;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private Integer motivoCambio;
        @NotNull(message = "%DEFAULT_BAD_REQUEST%")
        private String hash;
}

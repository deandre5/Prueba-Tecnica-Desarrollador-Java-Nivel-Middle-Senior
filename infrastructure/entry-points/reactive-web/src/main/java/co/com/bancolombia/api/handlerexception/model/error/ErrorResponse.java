package co.com.bancolombia.api.handlerexception.model.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ErrorResponse {
    @Schema(description = "Razón del error",
            example = "An error occurred on request")
    private String reason;
    @Schema(description = "Origen del error",
            example = "/api/v1/")
    private String domain;
    @Schema(description = "Código del error",
            example = "CCB0007")
    private String code;
    @Schema(description = "Mensaje del error",
            example = "An error occurred on request")
    private String message;
}

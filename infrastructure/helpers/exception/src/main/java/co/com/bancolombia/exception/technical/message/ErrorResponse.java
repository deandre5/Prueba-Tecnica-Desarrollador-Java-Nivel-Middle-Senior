package co.com.bancolombia.exception.technical.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String reason;
    private String domain;
    private String code;
    private String message;
    private String httpCode;
}

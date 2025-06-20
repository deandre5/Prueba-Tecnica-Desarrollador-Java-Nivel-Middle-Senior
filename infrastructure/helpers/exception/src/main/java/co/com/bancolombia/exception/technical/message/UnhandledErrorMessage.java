package co.com.bancolombia.exception.technical.message;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UnhandledErrorMessage {
    private final String code;
    private final String message;
    private final String statusCode;
}

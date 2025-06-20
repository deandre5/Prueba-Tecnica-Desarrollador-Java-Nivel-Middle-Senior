package co.com.bancolombia.model.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {

    DEFAULT_BAD_REQUEST("CCB0007", "An error occurred on request", "400"),
    DEFAULT_BAD_REQUEST_DATA("CCB0008", "An error occurred on request", "400"),
    INVALID_DATA_HASH("CCB0002", "An error occurred processing the hash", "400"),
    INVALID_HASH("CCB0002", "Invalid Hash", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_TOTAL_CLIENTS("CCB0009", "An error occurred on request, no total customers field was sent", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_CLAIM("CCB0010", "An error occurred on request, the field ReasonClaim has not been sent", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_WARRANTY("CCB0011", "An error occurred on request, The field Warranty reason was not sent.", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_DOUBT("CCB0012", "An error occurred on request, field was not sent reason for doubt", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_BUY("CCB0013", "An error occurred on request, the Purchase reason field was not sent", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_CONGRATS("CCB0014", "An error occurred on request, the reason field was not sent Congratulations", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_CHANGE("CCB0015", "An error occurred on request, no change reason field was sent", "400"),
    DEFAULT_BAD_REQUEST_STATISTICS_HASH("CCB0016", "An error occurred on request, no hash field was sent", "400");
    private final String code;
    private final String message;
    private final String statusCode;
}

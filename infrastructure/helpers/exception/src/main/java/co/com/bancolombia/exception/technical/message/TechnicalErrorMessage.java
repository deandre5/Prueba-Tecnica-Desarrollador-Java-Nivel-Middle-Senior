package co.com.bancolombia.exception.technical.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {
    INTERNAL_SERVER_ERROR("CCT0001", "Internal Server Error", "500"),
    BAD_REQUEST("CCT0006", "Invalid body", "400"),
    CONFLICT_REQUEST("CCT0007", "Conflict on request or invalid request information", "409"),
    UNEXPECTED_EXCEPTION("CDT0008", "Unexpected error", "500"),
    JSON_CONVERT_ERROR("CCT0417", "Json convert error","400");


    private final String code;
    private final String message;
    private final String statusCode;
}

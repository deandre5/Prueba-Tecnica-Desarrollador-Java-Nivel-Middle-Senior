package co.com.bancolombia.api.handlerexception;

import co.com.bancolombia.api.handlerexception.model.error.ErrorResponse;
import co.com.bancolombia.api.handlerexception.model.error.ErrorResponseData;
import co.com.bancolombia.exception.technical.TechnicalException;
import co.com.bancolombia.exception.technical.UnhandledException;
import co.com.bancolombia.exception.technical.message.TechnicalErrorMessage;
import co.com.bancolombia.exception.technical.message.UnhandledErrorMessage;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ExceptionHandlerTest {
    private static final Integer INDEX_DEFAULT = 0;
    @Mock
    ServerRequest serverRequest;

    @Mock
    DefaultErrorAttributes errorAttributes;

    @Autowired
    ApplicationContext applicationContext;

    @Mock
    ServerCodecConfigurer serverCodecConfigurer;

    @Mock
    ServerRequest.Headers headers;

    @Mock
    Throwable throwable;

    ExceptionHandler microServiceErrorHandler;

    HttpHeaders httpHeaders;

    UnhandledErrorMessage unhandledErrorMessage;

    @BeforeEach
    void init() {
        microServiceErrorHandler = new ExceptionHandler(errorAttributes, applicationContext,
                serverCodecConfigurer);
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Document-Id", "123");

        unhandledErrorMessage =  UnhandledErrorMessage.builder()
                .message("Test")
                .code("999")
                .statusCode("500")
                .build();
    }

    @Test
    void shouldFailAndGenerateAttributesTechnicalErrorResponse() {
        when(serverRequest.headers()).thenReturn(headers);
        when(headers.asHttpHeaders()).thenReturn(httpHeaders);
        when(errorAttributes.getError(any())).thenReturn(new TechnicalException(throwable,
                TechnicalErrorMessage.INTERNAL_SERVER_ERROR));
        StepVerifier.create(microServiceErrorHandler.renderErrorResponse(serverRequest))
                .expectError();
    }

    @Test
    void shouldFailAndGenerateAttributesBusinessErrorResponse() {
        when(serverRequest.headers()).thenReturn(headers);
        when(headers.asHttpHeaders()).thenReturn(httpHeaders);
        when(errorAttributes.getError(any())).thenReturn(new BusinessException(
                BusinessErrorMessage.DEFAULT_BAD_REQUEST));
        StepVerifier.create(microServiceErrorHandler.renderErrorResponse(serverRequest))
                .expectError();
    }

    @Test
    void shouldFailAndGenerateAttributesUnhandldedErrorResponse() {
        when(serverRequest.headers()).thenReturn(headers);
        when(headers.asHttpHeaders()).thenReturn(httpHeaders);
        when(errorAttributes.getError(any())).thenReturn(new UnhandledException(new Throwable(),
                unhandledErrorMessage));

        StepVerifier.create(microServiceErrorHandler.renderErrorResponse(serverRequest))
                .expectError();
    }

    @Test
    void shouldFailAndGenerateErrorResponse() {
        when(serverRequest.headers()).thenReturn(headers);
        when(headers.asHttpHeaders()).thenReturn(httpHeaders);
        when(errorAttributes.getError(any())).thenReturn(new NullPointerException());
        StepVerifier.create(microServiceErrorHandler.renderErrorResponse(serverRequest))
                .expectError();
    }

    @Test
    void shouldFailAndGenerateBusinessErrorResponse() {
        BusinessException businessException = new BusinessException(BusinessErrorMessage.INVALID_HASH);
        StepVerifier.create(microServiceErrorHandler.errorBuilder(businessException, "/test"))
                .expectNextMatches(res -> res.getT1().getErrors().get(INDEX_DEFAULT).getCode().equals("CCB0002"))
                .verifyComplete();
    }

    @Test
    void shouldFailAndGenerateTechnicalErrorResponse() {
        TechnicalException technicalException = new TechnicalException(new Throwable(),
                TechnicalErrorMessage.INTERNAL_SERVER_ERROR);
        StepVerifier.create(microServiceErrorHandler.errorBuilder(technicalException, "/test"))
                .expectNextMatches(res -> res.getT1().getErrors().get(INDEX_DEFAULT).getCode()
                        .equals(TechnicalErrorMessage.INTERNAL_SERVER_ERROR.getCode()))
                .verifyComplete();
    }

    @Test
    void shouldFailAndGenerateUnkwonErrorResponse() {
        StepVerifier.create(microServiceErrorHandler.errorBuilder("/test"))
                .expectNextMatches(res -> res.getT1().getErrors().get(INDEX_DEFAULT).getCode()
                        .equals(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode()))
                .verifyComplete();
    }

    @Test
    void shouldFailAndGenerateInternalServerErrorResponse() {
        ErrorResponse response = ErrorResponse.builder().code("500").build();
        StepVerifier.create(microServiceErrorHandler.buildResponse(ErrorResponseData.builder().errors(List.of(response))
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR))
                .expectNextMatches(res -> res.statusCode().value() == 500)
                .verifyComplete();
    }

    @Test
    void shouldFailAndGenerateUnhandledErrorResponse() {


        UnhandledException response = new UnhandledException(new Throwable(), unhandledErrorMessage);
        StepVerifier.create(microServiceErrorHandler.errorBuilder( response, "/test"))
                .expectNextMatches(res -> res.getT1().getErrors().get(INDEX_DEFAULT).getCode()
                        .equals("999"))
                .verifyComplete();
    }

}

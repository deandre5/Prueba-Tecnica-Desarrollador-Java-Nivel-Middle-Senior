package co.com.bancolombia.api.handlerexception;

import co.com.bancolombia.api.handlerexception.model.error.ErrorResponse;
import co.com.bancolombia.api.handlerexception.model.error.ErrorResponseData;
import co.com.bancolombia.exception.technical.TechnicalException;
import co.com.bancolombia.exception.technical.UnhandledException;
import co.com.bancolombia.exception.technical.message.TechnicalErrorMessage;
import co.com.bancolombia.model.exception.BusinessException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collections;
import java.util.Objects;

@Order(-2)
@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(DefaultErrorAttributes errorAttributes,
                            ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    public Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, ex -> this.errorBuilder(ex, request.path()))
                .onErrorResume(TechnicalException.class, ex -> this.errorBuilder(ex, request.path()))
                .onErrorResume(UnhandledException.class, ex -> this.errorBuilder(ex, request.path()))
                .onErrorResume(throwable -> this.errorBuilder(request.path()))
                .cast(Tuple2.class)
                .flatMap(tuple ->
                        this.buildResponse((ErrorResponseData) tuple.getT1(), (HttpStatus) tuple.getT2()));
    }

    public Mono<Tuple2<ErrorResponseData, HttpStatus>> errorBuilder(BusinessException businessException, String path) {
        return Mono.just(ErrorResponseData.builder().errors(
                        Collections.singletonList(ErrorResponse.builder()
                                .code(businessException.getErrorMessage().getCode())
                                .domain(path)
                                .reason(businessException.getErrorMessage().getMessage())
                                .message(businessException.getErrorMessage().getMessage()).build())).build())
                .zipWith(Mono.just(Objects.requireNonNull(HttpStatus
                        .resolve(Integer.parseInt(businessException.getErrorMessage().getStatusCode())))));
    }

    public Mono<Tuple2<ErrorResponseData, HttpStatus>> errorBuilder(TechnicalException technicalException,
                                                                    String path) {
        return Mono.just(ErrorResponseData.builder().errors(
                        Collections.singletonList(ErrorResponse.builder()
                                .code(technicalException.getTechnicalErrorMessage().getCode())
                                .domain(path)
                                .reason(technicalException.getTechnicalErrorMessage().getMessage())
                                .message(technicalException.getTechnicalErrorMessage().getMessage()).build())).build())
                .zipWith(Mono.just(Objects.requireNonNull(HttpStatus
                        .resolve(Integer.parseInt(technicalException.getTechnicalErrorMessage().getStatusCode())))));

    }

    public Mono<Tuple2<ErrorResponseData, HttpStatus>> errorBuilder(UnhandledException unhandledException,
                                                                    String path) {
        return Mono.just(ErrorResponseData.builder().errors(
                        Collections.singletonList(ErrorResponse.builder()
                                .code(unhandledException.getUnhandledErrorMessage().getCode())
                                .domain(path)
                                .reason(unhandledException.getUnhandledErrorMessage().getMessage())
                                .message(unhandledException.getUnhandledErrorMessage().getMessage()).build())).build())
                .zipWith(Mono.just(Objects.requireNonNull(HttpStatus
                        .resolve(Integer.parseInt(unhandledException.getUnhandledErrorMessage().getStatusCode())))));
    }

    public Mono<Tuple2<ErrorResponseData, HttpStatus>> errorBuilder(String path) {
        return Mono.just(ErrorResponseData.builder().errors(
                        Collections.singletonList(ErrorResponse.builder()
                                .code(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode())
                                .domain(path)
                                .reason(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
                                .message(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage()).build())).build())
                .zipWith(Mono.just(Objects.requireNonNull(HttpStatus
                        .resolve(Integer.parseInt(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getStatusCode())))));
    }

    public Mono<ServerResponse> buildResponse(ErrorResponseData errors, HttpStatus httpStatus) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(errors), ErrorResponseData.class);
    }
}

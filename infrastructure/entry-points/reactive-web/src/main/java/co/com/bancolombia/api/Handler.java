package co.com.bancolombia.api;

import co.com.bancolombia.api.statistics.StatisticsRequestDataDTO;
import co.com.bancolombia.api.utils.RequestFactory;
import co.com.bancolombia.api.utils.ResponseFactory;
import co.com.bancolombia.exception.technical.TechnicalException;
import co.com.bancolombia.exception.technical.message.TechnicalErrorMessage;
import co.com.bancolombia.usecase.savestatistics.SaveStatisticsUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final SaveStatisticsUseCase saveStatisticsUseCase;
    public Mono<ServerResponse> saveStatistics(@NonNull ServerRequest serverRequest) {
        return serverRequest.bodyToMono(StatisticsRequestDataDTO.class)
                .switchIfEmpty(Mono.error(new TechnicalException(TechnicalErrorMessage.BAD_REQUEST)))
                .map(StatisticsRequestDataDTO::validate)
                .map(RequestFactory::getRequest)
                .flatMap(saveStatisticsUseCase::validateStatistics)
                .map(ResponseFactory::getResponse)
                .flatMap(responseStatistics -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseStatistics));
    }
}



package co.com.bancolombia.api;

import co.com.bancolombia.api.statistics.StatisticsRequestDataDTO;
import co.com.bancolombia.api.statistics.StatisticsResponseDTO;
import co.com.bancolombia.api.statistics.StatisticsResponseDataDTO;
import co.com.bancolombia.api.utils.RequestFactory;
import co.com.bancolombia.api.utils.ResponseFactory;
import co.com.bancolombia.exception.technical.TechnicalException;
import co.com.bancolombia.exception.technical.message.TechnicalErrorMessage;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.statistics.Statistics;
import co.com.bancolombia.usecase.savestatistics.SaveStatisticsUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerTest {

    @Mock
    private SaveStatisticsUseCase saveStatisticsUseCase;

    @InjectMocks
    private Handler handler;

    private AutoCloseable closeable;

    @Mock
    private RequestFactory requestFactory;

    @Mock
    private ResponseFactory responseFactory;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

    }

    @Test
    void saveStatisticsSuccess() {
        ServerRequest serverRequest = mock(ServerRequest.class);

        StatisticsRequestDataDTO requestDto = mock(StatisticsRequestDataDTO.class);
        StatisticsRequestDataDTO.StatisticsDataDTO dataDto = mock(StatisticsRequestDataDTO.StatisticsDataDTO.class);

        when(requestDto.validate()).thenReturn(requestDto);
        when(requestDto.getData()).thenReturn(dataDto);

        Statistics statisticsModelInput = Statistics.builder().hash("testHash").build();
        Statistics statisticsModelOutput = Statistics.builder().hash("testHash").timeStamp("123456789").build();
        StatisticsResponseDataDTO responseDataDto = StatisticsResponseDataDTO.builder()
                .data(StatisticsResponseDataDTO.StatisticsDataDTO.builder()
                        .statistics(StatisticsResponseDTO.builder().timeStamp("123456789").build())
                        .build())
                .build();

        when(serverRequest.bodyToMono(StatisticsRequestDataDTO.class)).thenReturn(Mono.just(requestDto));
        when(saveStatisticsUseCase.validateStatistics(any(Statistics.class))).thenReturn(Mono.just(statisticsModelOutput));

        try (MockedStatic<RequestFactory> mockedRequestFactory = Mockito.mockStatic(RequestFactory.class);
             MockedStatic<ResponseFactory> mockedResponseFactory = Mockito.mockStatic(ResponseFactory.class)) {

            mockedRequestFactory.when(() -> RequestFactory.getRequest(any(StatisticsRequestDataDTO.class)))
                    .thenReturn(statisticsModelInput);

            mockedResponseFactory.when(() -> ResponseFactory.getResponse(any(Statistics.class)))
                    .thenReturn(responseDataDto);

            Mono<ServerResponse> responseMono = handler.saveStatistics(serverRequest);

            StepVerifier.create(responseMono)
                    .expectNextMatches(serverResponse -> {
                        if (serverResponse.statusCode() != HttpStatus.OK) {
                            return false;
                        }
                        return serverResponse.headers().getContentType().equals(MediaType.APPLICATION_JSON);
                    })
                    .verifyComplete();

            Mockito.verify(saveStatisticsUseCase).validateStatistics(any(Statistics.class));
            mockedRequestFactory.verify(() -> RequestFactory.getRequest(any(StatisticsRequestDataDTO.class)));
            mockedResponseFactory.verify(() -> ResponseFactory.getResponse(any(Statistics.class)));
        }
    }

    @Test
    void saveStatisticsEmptyBodyShouldReturnBadRequest() {
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(StatisticsRequestDataDTO.class)).thenReturn(Mono.empty());

        Mono<ServerResponse> responseMono = handler.saveStatistics(serverRequest);

        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable ->
                        throwable instanceof TechnicalException &&
                                ((TechnicalException) throwable).getTechnicalErrorMessage().equals(TechnicalErrorMessage.BAD_REQUEST)
                )
                .verify();

        Mockito.verifyNoInteractions(saveStatisticsUseCase);
    }

    @Test
    void saveStatisticsValidationFailureShouldPropagateBusinessException() {
        ServerRequest serverRequest = mock(ServerRequest.class);
        StatisticsRequestDataDTO requestDto = mock(StatisticsRequestDataDTO.class);

        when(serverRequest.bodyToMono(StatisticsRequestDataDTO.class)).thenReturn(Mono.just(requestDto));
        when(requestDto.validate()).thenThrow(new BusinessException(BusinessErrorMessage.DEFAULT_BAD_REQUEST));

        Mono<ServerResponse> responseMono = handler.saveStatistics(serverRequest);

        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                ((BusinessException) throwable).getErrorMessage().equals(BusinessErrorMessage.DEFAULT_BAD_REQUEST)
                )
                .verify();

        Mockito.verifyNoInteractions(saveStatisticsUseCase);
    }



    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

}

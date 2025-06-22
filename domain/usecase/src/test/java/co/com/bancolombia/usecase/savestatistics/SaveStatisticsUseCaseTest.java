package co.com.bancolombia.usecase.savestatistics;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.statistics.Statistics;
import co.com.bancolombia.model.statistics.gateways.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SaveStatisticsUseCaseTest {
    @InjectMocks
    private SaveStatisticsUseCase saveStatisticsUseCase;

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private EventsGateway eventsGateway;

    @BeforeEach
    void setUp(){
        saveStatisticsUseCase = new SaveStatisticsUseCase(statisticsRepository, eventsGateway);
    }

    @Test
    void succesfullyExecution(){
        when(statisticsRepository.save(any()))
                .thenReturn(Mono.just(DataFactory.StatisticsValidatedBuild()));
        when(eventsGateway.emit(any()))
                .thenReturn(Mono.empty());

        Mono<Statistics> result =saveStatisticsUseCase.validateStatistics(DataFactory.StatisticsCorrectHashBuild());

        StepVerifier.create(result)
                .expectNextMatches(Objects::nonNull)
                .verifyComplete();
    }

    @Test
    void badHashExecution(){
        when(statisticsRepository.save(any()))
                .thenReturn(Mono.just(DataFactory.StatisticsValidatedBuild()));
        when(eventsGateway.emit(any()))
                .thenReturn(Mono.empty());

        Mono<Statistics> result = saveStatisticsUseCase.validateStatistics(DataFactory.StatisticsWrongHashBuild());

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage()
                        .equals(BusinessErrorMessage.INVALID_HASH.getMessage()))
                .verify();
    }

}

package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.DataFactory;
import co.com.bancolombia.model.statistics.Statistics;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DynamoDBStatisticsRepositoryTest {

    @Mock
    private StatisticsRepositoryAdapter statisticsAdapter;

    @InjectMocks
    private DynamoDBStatisticsRepository statisticsRepository;

    private Statistics testStatistics;


    @BeforeEach
    void setUp(){
        testStatistics = DataFactory.statisticsBuild();
    }

    @Test
    void shouldCallSaveOnAdapterAndReturnMono() {

        when(statisticsAdapter.save(any(Statistics.class)))
                .thenReturn(Mono.just(testStatistics));

        Mono<Statistics> result = statisticsRepository.save(testStatistics);

        StepVerifier.create(result)
                .expectNextMatches(stats ->
                                stats.getHash().equals(testStatistics.getHash()) &&
                                        stats.getTotalContactoClientes() == (testStatistics.getTotalContactoClientes())
                ).verifyComplete();
    }

    @Test
    void shouldPropagateErrorFromAdapterSave() {

        RuntimeException expectedError = new RuntimeException("Error simulado al guardar");
        when(statisticsAdapter.save(any(Statistics.class)))
                .thenReturn(Mono.error(expectedError));

        Mono<Statistics> result = statisticsRepository.save(testStatistics);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Error simulado al guardar")
                ).verify();
    }
}
package co.com.bancolombia.dynamodb;


import co.com.bancolombia.dynamodb.helper.DataFactory;
import co.com.bancolombia.model.statistics.Statistics;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StatisticsRepositoryAdapterTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private DynamoDbAsyncTable<StatisticsModelEntity> statisticsTable;

    private StatisticsRepositoryAdapter statisticsRepositoryAdapter;

    private Statistics domainStatistics;

    private StatisticsModelEntity expectedEntity;

    private final String tableName = "mueblesStats";
    @BeforeEach
    void setUp() {
        when(dynamoDbEnhancedAsyncClient.table(eq(tableName), any(TableSchema.class)))
                .thenReturn(statisticsTable);

        statisticsRepositoryAdapter = new StatisticsRepositoryAdapter(
                dynamoDbEnhancedAsyncClient,
                objectMapper,
                tableName
        );

        domainStatistics = DataFactory.statisticsBuild();


        expectedEntity = StatisticsModelEntity.builder()
                .totalContactoClientes(10)
                .motivoReclamo(1)
                .motivoGarantia(2)
                .motivoDuda(3)
                .motivoCompra(4)
                .motivoFelicitaciones(5)
                .motivoCambio(6)
                .hash("testHash")
                .timeStamp("2025-06-21T10:00:00Z")
                .build();
    }

    @Test
    void shouldCreateAdapterSuccessfully() {
        assertNotNull(statisticsRepositoryAdapter);
        verify(dynamoDbEnhancedAsyncClient).table(eq(tableName), any(TableSchema.class));
    }

    @Test
    void shouldSaveStatisticsSuccessfully() {

        expectedEntity.setTimeStamp("2025-06-21T10:00:00Z");

        doReturn(expectedEntity)
                .when(objectMapper)
                .map(any(Statistics.class), eq(StatisticsModelEntity.class));

        when(statisticsTable.putItem(any(StatisticsModelEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        Mono<Statistics> result = statisticsRepositoryAdapter.save(domainStatistics);

        StepVerifier.create(result)
                .expectNext(domainStatistics)
                .verifyComplete();
    }

    @Test
    void shouldFailToSaveStatisticsOnError() {
        doReturn(expectedEntity)
                .when(objectMapper)
                .map(any(Statistics.class), eq(StatisticsModelEntity.class));


        when(statisticsTable.putItem(any(StatisticsModelEntity.class)))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Error simulado de DynamoDB")));

        Mono<Statistics> result = statisticsRepositoryAdapter.save(domainStatistics);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error simulado de DynamoDB"))
                .verify();
    }

    @Test
    void shouldMapEntityToDomainCorrectly() {
        // Asegúrate que el getItem con el Key correcto devuelva el expectedEntity
        when(statisticsTable.getItem(Key.builder().partitionValue(expectedEntity.getHash()).build()))
                .thenReturn(CompletableFuture.completedFuture(expectedEntity));

        Mono<Statistics> result = Mono.fromFuture(statisticsTable.getItem(Key.builder().partitionValue(expectedEntity.getHash()).build()))
                .map(data -> Statistics.builder() // <-- Esta lambda
                        .totalContactoClientes(data.getTotalContactoClientes())
                        .motivoReclamo(data.getMotivoReclamo())
                        .motivoGarantia(data.getMotivoGarantia())
                        .motivoFelicitaciones(data.getMotivoFelicitaciones())
                        .motivoDuda(data.getMotivoDuda())
                        .motivoCompra(data.getMotivoCompra())
                        .motivoCambio(data.getMotivoCambio())
                        .hash(data.getHash())
                        .timeStamp(data.getTimeStamp())
                        .build());

        StepVerifier.create(result)
                .expectNextMatches(stats ->
                        stats.getTotalContactoClientes() == (expectedEntity.getTotalContactoClientes()) &&
                                // ... (resto de las aserciones)
                                stats.getTimeStamp().equals(expectedEntity.getTimeStamp())
                )
                .verifyComplete();
    }
}
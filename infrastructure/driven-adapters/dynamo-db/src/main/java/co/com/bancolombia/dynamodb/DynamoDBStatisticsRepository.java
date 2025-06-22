package co.com.bancolombia.dynamodb;

import co.com.bancolombia.model.statistics.Statistics;
import co.com.bancolombia.model.statistics.gateways.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DynamoDBStatisticsRepository implements StatisticsRepository {
    private final StatisticsRepositoryAdapter statisticsAdapter;

    @Override
    public Mono<Statistics> save(Statistics statistics) {
        return statisticsAdapter.save(statistics);
    }
}

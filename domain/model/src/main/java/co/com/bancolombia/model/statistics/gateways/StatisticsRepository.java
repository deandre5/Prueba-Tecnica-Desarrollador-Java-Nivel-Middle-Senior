package co.com.bancolombia.model.statistics.gateways;

import co.com.bancolombia.model.statistics.Statistics;
import reactor.core.publisher.Mono;

public interface StatisticsRepository {
    Mono<Statistics> save(Statistics statistics);
}

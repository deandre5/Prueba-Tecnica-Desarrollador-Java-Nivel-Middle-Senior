package co.com.bancolombia.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "entry-points.reactive-web")
public class RouteProperty {
    private StatisticsRoute statisticsRoute;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsRoute {
        private String validateStats;
    }
}

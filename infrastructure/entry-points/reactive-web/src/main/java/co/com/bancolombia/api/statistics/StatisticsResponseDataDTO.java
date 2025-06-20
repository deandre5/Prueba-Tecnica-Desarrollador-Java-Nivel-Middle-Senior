package co.com.bancolombia.api.statistics;

import co.com.bancolombia.model.statistics.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsResponseDataDTO {
    private StatisticsDataDTO data;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class StatisticsDataDTO{
        private StatisticsResponseDTO statistics;
    }
}

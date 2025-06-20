package co.com.bancolombia.api.utils;

import co.com.bancolombia.api.statistics.StatisticsResponseDTO;
import co.com.bancolombia.api.statistics.StatisticsResponseDataDTO;
import co.com.bancolombia.model.statistics.Statistics;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseFactory {

    public static StatisticsResponseDataDTO getResponse(Statistics statistics){
        return StatisticsResponseDataDTO.builder()
                .data(StatisticsResponseDataDTO.StatisticsDataDTO.builder()
                        .statistics(
                                StatisticsResponseDTO.builder()
                                        .timeStamp(statistics.getTimeStamp()).build()
                        ).build())
                .build();
    }
}

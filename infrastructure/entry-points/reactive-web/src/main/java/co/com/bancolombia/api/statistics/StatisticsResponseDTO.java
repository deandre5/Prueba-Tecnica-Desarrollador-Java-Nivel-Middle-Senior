package co.com.bancolombia.api.statistics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsResponseDTO {
    private String timeStamp;
}
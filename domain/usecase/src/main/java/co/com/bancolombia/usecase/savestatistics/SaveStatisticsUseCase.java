package co.com.bancolombia.usecase.savestatistics;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.statistics.Statistics;
import co.com.bancolombia.model.statistics.gateways.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;

@RequiredArgsConstructor
public class SaveStatisticsUseCase {
    private final StatisticsRepository statisticsRepository;
    private final EventsGateway eventsGateway;


    public Mono<Statistics> validateStatistics(Statistics statistics){
        String generateHash = generateHash(statistics);

        if(!generateHash.equals(statistics.getHash())){
            return Mono.error(new BusinessException(BusinessErrorMessage.INVALID_HASH));
        }

        Statistics validHash = statistics.toBuilder()
                                        .timeStamp(Instant.now().toString())
                                        .build();

        return statisticsRepository.save(validHash)
                .flatMap(savedStats -> eventsGateway.emit(savedStats)
                        .thenReturn(savedStats));
    }

    private String generateHash(Statistics statistics){
        try{
            String data = String.format("%d,%d,%d,%d,%d,%d,%d",
                    statistics.getTotalContactoClientes(),
                    statistics.getMotivoReclamo(),
                    statistics.getMotivoGarantia(),
                    statistics.getMotivoDuda(),
                    statistics.getMotivoCompra(),
                    statistics.getMotivoFelicitaciones(),
                    statistics.getMotivoCambio());

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e){
            throw new BusinessException(BusinessErrorMessage.INVALID_DATA_HASH);
        }
    }

}

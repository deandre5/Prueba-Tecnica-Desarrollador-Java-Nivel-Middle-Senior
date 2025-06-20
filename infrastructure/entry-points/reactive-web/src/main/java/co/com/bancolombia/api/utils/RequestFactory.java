package co.com.bancolombia.api.utils;

import co.com.bancolombia.api.statistics.StatisticsRequestDataDTO;
import co.com.bancolombia.model.statistics.Statistics;
import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestFactory {
    public static Statistics getRequest(StatisticsRequestDataDTO statistics){
        return  Statistics.builder()
                .hash(statistics.getData().getStatistics().getHash())
                .motivoCambio(statistics.getData().getStatistics().getMotivoCambio())
                .motivoCompra(statistics.getData().getStatistics().getMotivoCompra())
                .motivoDuda(statistics.getData().getStatistics().getMotivoDuda())
                .motivoFelicitaciones(statistics.getData().getStatistics().getMotivoFelicitaciones())
                .motivoGarantia(statistics.getData().getStatistics().getMotivoGarantia())
                .motivoReclamo(statistics.getData().getStatistics().getMotivoReclamo())
                .totalContactoClientes(statistics.getData().getStatistics().getTotalContactoClientes())
                .build();
    }
}

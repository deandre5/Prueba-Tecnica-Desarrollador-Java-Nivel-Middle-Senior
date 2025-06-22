package co.com.bancolombia.dynamodb.helper;

import co.com.bancolombia.model.statistics.Statistics;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataFactory {

    public Statistics statisticsBuild(){
        return Statistics.builder()
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
}

package co.com.bancolombia.usecase.savestatistics;

import co.com.bancolombia.model.statistics.Statistics;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataFactory {

    public static Statistics StatisticsWrongHashBuild(){
        return Statistics.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .hash("02946f262f2eb0d8d5c8e76c50433ed8")
                .build();
    }

    public static Statistics StatisticsCorrectHashBuild(){
        return Statistics.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .hash("5484062a4be1ce5645eb414663e14f59")
                .build();
    }

    public static Statistics StatisticsValidatedBuild(){
        return Statistics.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .hash("5484062a4be1ce5645eb414663e14f59")
                .timeStamp("2025-06-20T23:03:33.680055500Z")
                .build();
    }
}

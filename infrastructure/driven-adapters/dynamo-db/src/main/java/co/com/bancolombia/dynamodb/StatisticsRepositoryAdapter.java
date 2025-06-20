package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.statistics.Statistics;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.w3c.dom.CDATASection;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;



@Repository
public class StatisticsRepositoryAdapter extends TemplateAdapterOperations<Statistics, String,StatisticsModelEntity  > {


    public StatisticsRepositoryAdapter(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, ObjectMapper objectMapper, @Value("${dynamodb.table-name}") String tableName){
        super(dynamoDbEnhancedAsyncClient,
                objectMapper,
                data -> Statistics.builder()
                        .totalContactoClientes(data.getTotalContactoClientes())
                        .motivoReclamo(data.getMotivoReclamo())
                        .motivoGarantia(data.getMotivoGarantia())
                        .motivoFelicitaciones(data.getMotivoFelicitaciones())
                        .motivoDuda(data.getMotivoDuda())
                        .motivoCompra(data.getMotivoCompra())
                        .motivoCambio(data.getMotivoCambio())
                        .hash(data.getHash())
                        .timeStamp(data.getTimeStamp())
                        .build(),
                tableName);
    }
}

package co.com.bancolombia.dynamodb.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DynamoDBConfig.class)
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "adapters.aws.dynamodb.endpoint=http://localhost:8000",
        "adapters.aws.region=us-east-1"
})
class DynamoDBConfigLocalTest {

    @Autowired(required = false)
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    @Autowired
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Test

    void shouldConfigureLocalDynamoDBClient() {
        assertNotNull(dynamoDbAsyncClient, "DynamoDbAsyncClient should be configured for 'local' profile");
        assertNotNull(dynamoDbEnhancedAsyncClient, "DynamoDbEnhancedAsyncClient should be configured");
    }
}

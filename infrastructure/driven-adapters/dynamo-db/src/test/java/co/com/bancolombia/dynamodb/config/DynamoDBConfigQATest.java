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
@ActiveProfiles("cer")
@TestPropertySource(properties = {
        "aws.region=us-east-1"
})
class DynamoDBConfigQATest {

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    @Autowired
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Test
    void shouldConfigureDynamoDBClientsForActiveProfile() {
        assertNotNull(dynamoDbAsyncClient, "DynamoDbAsyncClient should be configured for the active profile");
        assertNotNull(dynamoDbEnhancedAsyncClient, "DynamoDbEnhancedAsyncClient should be configured");
    }
}

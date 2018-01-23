package example.customerdocuments;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class CustomerDocumentRepositoryInitializer implements CommandLineRunner {

  private final AmazonDynamoDB amazonDynamoDB;
  private final DynamoDBMapperConfig dynamoDBMapperConfig;
  private final DynamoDBMapper dynamoDBMapper;

  @Override
  public void run(String... args) throws Exception {

    createTableIfNecessary();

  }

  private void createTableIfNecessary()
      throws InterruptedException {

    CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(CustomerDocument.class, dynamoDBMapperConfig);

    String tableName = createTableRequest.getTableName();

    try {

      amazonDynamoDB.describeTable(tableName);

      log.info(String.format("Table '%s' found.", tableName));

    } catch (ResourceNotFoundException resourceNotFoundException) {

      createTableRequest.setProvisionedThroughput(
          new ProvisionedThroughput()
              .withReadCapacityUnits(5L)
              .withWriteCapacityUnits(5L)
      );

      log.info(String.format("Table '%s' was not found, so it's being created using %s.", tableName, createTableRequest));

      DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

      Table table = dynamoDB.createTable(createTableRequest);

      table.waitForActive();

      log.info(String.format("Table '%s' was successfully created.", tableName));

    }

  }

}

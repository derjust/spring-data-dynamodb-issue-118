package example.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import example.customerdocuments.CustomerDocument;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableDynamoDBRepositories(
    dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
    basePackageClasses = {CustomerDocument.class}
)
public class DynamoDbConfig {

  @Value("${aws.region}")
  private String amazonAWSRegion;

  @Value("${aws.accesskey}")
  private String amazonAWSAccessKey;

  @Value("${aws.secretkey}")
  private String amazonAWSSecretKey;

  @Bean
  public AWSCredentials amazonAWSCredentials() {

    return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);

  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB(AWSCredentials awsCredentials) {

    return AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.fromName(amazonAWSRegion))
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();

  }

  @Bean
  public DynamoDBMapperConfig dynamoDBMapperConfig() {

    return DynamoDBMapperConfig.builder()
        .withConversionSchema(ConversionSchemas.V2)
        .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
        .build();

  }

  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB dynamoDB) {
    return new DynamoDBMapper(dynamoDB);
  }

}

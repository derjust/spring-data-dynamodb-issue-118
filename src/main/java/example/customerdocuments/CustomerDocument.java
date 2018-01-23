package example.customerdocuments;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName = "CustomerDocuments")
public class CustomerDocument {

  @Id
  private CustomerDocumentId customerDocumentId;

  @DynamoDBAttribute
  private String s3Location;

  public CustomerDocument(String customerId, String documentType, String version, String s3Location) {
    this.customerDocumentId = new CustomerDocumentId(customerId, documentType, version);
    this.s3Location = s3Location;
  }

  @DynamoDBHashKey(attributeName = "customerId|documentType")
  public String getCustomerDocumentKey() {

    if (customerDocumentId == null) {
      return null;
    }

    return customerDocumentId.getCustomerDocumentKey();

  }

  public void setCustomerDocumentKey(String customerDocumentKey) {

    if (customerDocumentId == null) {
      this.customerDocumentId = new CustomerDocumentId();
    }

    customerDocumentId.setCustomerDocumentKey(customerDocumentKey);

  }

  @DynamoDBRangeKey(attributeName = "version")
  public String getVersion() {

    if (customerDocumentId == null) {
      return null;
    }

    return customerDocumentId.getVersion();

  }

  public void setVersion(String version) {

    if (customerDocumentId == null) {
      this.customerDocumentId = new CustomerDocumentId();
    }

    customerDocumentId.setVersion(version);

  }

  @DynamoDBIgnore
  public String getCustomerId() {

    if (customerDocumentId == null) {
      return null;
    }

    return customerDocumentId.getCustomerId();

  }

  @DynamoDBIgnore
  public String getDocumentType() {

    if (customerDocumentId == null) {
      return null;
    }

    return customerDocumentId.getDocumentType();

  }

  @DynamoDBIgnore
  public CustomerDocumentId getCustomerDocumentId() {
    return customerDocumentId;
  }

}
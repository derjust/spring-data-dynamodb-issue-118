package example;

import example.customerdocuments.CustomerDocument;
import example.customerdocuments.CustomerDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ExampleRunner implements CommandLineRunner {

  private final CustomerDocumentRepository customerDocumentRepository;

  @Override
  public void run(String... args) throws Exception {

    CustomerDocument newCustomerDocument = new CustomerDocument("1", "terms", "0.0.1", "1/readme/0.0.1/doc.md");

    customerDocumentRepository.save(newCustomerDocument);

    log.info("Stored " + newCustomerDocument);

    log.info("All Customer Documents matching hashkey '1|terms':");

    try {

      customerDocumentRepository.findByCustomerDocumentKey("1", "terms")
          .forEach(customerDocument -> log.info(customerDocument.toString()));

    } catch (Exception e) {
      log.error("Error occurred retrieving records based on '1|terms'.", e);
    }

  }

}
package by.dma.springboottesting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import by.dma.springboottesting.domains.customer.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Run all integration tests with: mvn failsafe:integration-test failsafe:verify
 */
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.security.user.name=admin",
        "spring.security.user.password=admin#"
    })
class SpringBootTestDemoReusedContextTests {

  @Autowired
  private TestRestTemplate testClient;

  @Test
  void shouldFindQuestionById() {

    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth("admin", "admin#");

    HttpEntity<Customer> request = new HttpEntity<>(headers);

    ResponseEntity<Customer> result = testClient
        .exchange("/api/questions/1", HttpMethod.GET, request, Customer.class);

    assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
  }
}

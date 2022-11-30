package by.dma.springboottesting.integrationtests;

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
 * This integration test can reuse an already started application context to avoid long test runs.
 * Spring will cache all started TestContexts until the internal cache limit is reached.
 * The default cache size is 32.
 * The context caching can drastically improve our build times as starting a fresh Spring TestContext,
 * especially for integration tests, takes quite some time
 * <p>
 * Run all integration tests with: `mvn failsafe:integration-test failsafe:verify`.
 */
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.security.user.name=admin",
                "spring.security.user.password=admin#"
        })
class ReusedContextSpringBootIT {

    @Autowired
    private TestRestTemplate testClient;

    @Test
    void shouldFindQuestionById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin#");

        HttpEntity<Customer> request = new HttpEntity<>(headers);

        ResponseEntity<Customer> result = testClient
                .exchange("/api/questions/101", HttpMethod.GET, request, Customer.class);

        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }
}

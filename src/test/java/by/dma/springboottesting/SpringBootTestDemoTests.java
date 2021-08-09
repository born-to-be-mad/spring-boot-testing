package by.dma.springboottesting;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import by.dma.springboottesting.domain.Customer;
import by.dma.springboottesting.repository.QuestionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Run all integration tests with: mvn failsafe:integration-test failsafe:verify
 */
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.security.user.name=admin",
                "spring.security.user.password=admin#"
                //,"server.port=8042",
                //"management.server.port=9042"
        })

class SpringBootTestDemoTests {
    @LocalServerPort
    private Integer port;

    @LocalManagementPort
    private Integer managementPort;

    @Autowired
    private TestRestTemplate testClient;

    @MockBean
    private QuestionRepository questionRepository;

    @BeforeEach
    void printPortsInUse() {
        System.out.println("LocalServerPort:" + port);
        System.out.println("LocalManagementPort:" + managementPort);
    }

    @Test
    void shouldFetchListOfQuestions() {
        // given:
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin#");

        HttpEntity<List<Customer>> request = new HttpEntity<>(headers);

        // when:
        ResponseEntity<List<Customer>> result = testClient
                .exchange("/api/questions", HttpMethod.GET, request,
                          new ParameterizedTypeReference<>() {});

        // then:
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void shouldFailFetchingQuestionsWhenDatabaseIsDown() {
        when(questionRepository.findAll()).thenThrow(new RuntimeException("Database is down :("));

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin#");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> result = testClient.exchange("/api/questions", HttpMethod.GET, request, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCodeValue());
    }
}

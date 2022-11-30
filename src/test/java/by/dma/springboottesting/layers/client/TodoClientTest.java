package by.dma.springboottesting.layers.client;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @RestClientTest annotation is used to combine (a lot of) other annotations mostly used for auto-configuration.
 * Together all of these annotations only bootstrap the required beans to test a specific part of the application.
 */
@RestClientTest(TodoClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class TodoClientTest {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private TodoClient client;

    @AfterEach
    void tearDown() {
        // reset all expectations/recorded requests and verify that every expectation was actually used
        mockRestServiceServer.verify();
    }

    @Test
    void shouldReturnListOfTodos() {
        final String responseBody =
                "[{" +
                        "\"id\": 1," +
                        "\"userId\": 42, " +
                        "\"title\":\"Learn Testing Spring Boot Applications\", " +
                        "\"completed\": false" +
                        "}]";
        mockRestServiceServer
                .expect(requestTo("/todos"))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<Todo> result = client.fetchAllTodos();

        assertEquals(1, result.size());
    }

    @Test
    void shouldPropagateFailure() {
        mockRestServiceServer
                .expect(requestTo("/todos"))
                .andRespond(withServerError());

        assertThrows(RuntimeException.class, () -> client.fetchAllTodos());
    }
}

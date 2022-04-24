package by.dma.springboottesting.layers.client;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import by.dma.springboottesting.layers.client.Todo;
import by.dma.springboottesting.layers.client.TodoClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(TodoClient.class)
class TodoClientTest {
    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private TodoClient client;

    @Test
    void shouldReturnListOfTodos() {
        final String responseBody =
                "[{" +
                        "\"id\": 1," +
                        "\"userId\": 42, " +
                        "\"title\":\"Learn Testing Spring Boot Applications\", " +
                        "\"completed\": false" +
                        "}]";
        this.server.expect(requestTo("/todos"))
                   .andRespond(
                           withSuccess(responseBody,
                MediaType.APPLICATION_JSON));

        List<Todo> result = client.fetchAllTodos();

        assertEquals(1, result.size());
    }

    @Test
    void shouldPropagateFailure() {
        this.server.expect(requestTo("/todos")).andRespond(withServerError());

        assertThrows(RuntimeException.class, () -> client.fetchAllTodos());
    }
}

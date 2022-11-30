package by.dma.springboottesting.layers.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.HttpClientErrorException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author dzmitry.marudau
 * @RestClientTest annotation is used to combine (a lot of) other annotations mostly used for auto-configuration.
 * Together all of these annotations only bootstrap the required beans to test a specific part of the application.
 * @since 2022.11
 */
@RestClientTest(UserClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class UserClientTest {

    @Autowired
    private UserClient userClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    void userClientSuccessfullyReturnsUser() {

        String json =
                "{"
                + "  \"id\": 1,"
                + "  \"email\": \"janet.weaver@reqres.in\","
                + "  \"first_name\": \"Janet\","
                + "  \"last_name\": \"Weaver\","
                + "  \"avatar\": \"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\""
                + "}";

        mockRestServiceServer
                .expect(requestTo("/api/users/1"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        User result = userClient.getSingleUser(1L);

        assertNotNull(result);
    }

    @Test
    void userClientSuccessfullyReturnsUserDuke() throws Exception {

        String json = this.objectMapper
                .writeValueAsString(new User(42L, "duke@java.org", "duke", "duke", "duke"));

        mockRestServiceServer
                .expect(requestTo("/api/users/42"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        User result = userClient.getSingleUser(42L);

        assertEquals(42L, result.getId());
        assertEquals("duke", result.getFirstName());
        assertEquals("duke", result.getLastName());
        assertEquals("duke", result.getAvatar());
        assertEquals("duke@java.org", result.getEmail());
    }

    @Test
    void userClientThrowsExceptionWhenNoUserIsFound() {
        this.mockRestServiceServer.expect(requestTo("/api/users/1"))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, () -> userClient.getSingleUser(1L));
    }
}
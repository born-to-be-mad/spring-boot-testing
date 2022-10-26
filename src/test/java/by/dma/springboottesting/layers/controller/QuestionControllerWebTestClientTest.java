package by.dma.springboottesting.layers.controller;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.service.QuestionService;

/**
 * WebTestClient is the testing counterpart of the Spring Webflux WebClient.
 * Although its non-blocking origin, we can still use it for blocking applications.
 * The primary purpose is to verify controller endpoints of Spring WebFlux applications in combination with @WebFluxTest.
 * The Spring team has extended the use cases for the WebTestClient. We can use it for both tests that work with a mocked-servlet environment (MockMvc) and for integration tests against a running servlet container.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionControllerWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private QuestionService service;

    @Test
    void shouldReturnOK() {
        webTestClient
            .get().uri("/api/questions")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void shouldReturnListOfQuestions() {
        // given:
        Question sample = new Question();
        sample.setId(123L);
        sample.setSortOrder(2);
        sample.setName("What is your favorite social network?");
        sample.setCreatedAt(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Minsk")));
        Mockito.when(service.getAllQuestions()).thenReturn(List.of(sample));

        webTestClient.get().uri("/api/questions").exchange().expectStatus().isOk().expectBody().jsonPath("$.size()")
                     .isEqualTo(1).jsonPath("$[0].id")
                     .isEqualTo(123).jsonPath("$[0].name")
                     .isEqualTo("What is your favorite social network?")
                     .jsonPath("$[0].createdAt").isEqualTo("2020-01-01T00:00:00+03:00");
    }

    @Test
    void shouldForbidAnonymousUsersFetchingQuestionById() throws Exception {
        webTestClient.get().uri("/api/questions/123")
                     .exchange()
                     .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    @Disabled("This test is disabled because it is not working with Spring Security")
    void shouldAllowAuthenticatedUsersToFetchQuestionById() {
        // given:
        Question sample = new Question();
        sample.setId(123L);
        sample.setSortOrder(2);
        sample.setName("What is your favorite social network?");
        sample.setCreatedAt(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Minsk")));

        Mockito.when(service.getQuestionById(123L)).thenReturn(sample);

        webTestClient
            .get().uri("/api/questions/123")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(123)
            .jsonPath("$.name").isEqualTo("What is your favorite social network?")
            .jsonPath("$.createdAt").isEqualTo("2020-01-01T00:00:00+03:00");
    }
}

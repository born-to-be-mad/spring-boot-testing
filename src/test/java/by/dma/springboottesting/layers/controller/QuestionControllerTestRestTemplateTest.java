package by.dma.springboottesting.layers.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.service.QuestionService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private QuestionService service;

    @Test
    void shouldReturnOK() {
        ResponseEntity<List<Question>> response =
                testRestTemplate.exchange("/api/questions", HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnListOfQuestions() {
        // given:
        Question sample = new Question();
        sample.setId(123L);
        sample.setSortOrder(2);
        sample.setName("What is your favorite social network?");
        Mockito.when(service.getAllQuestions()).thenReturn(List.of(sample));

        // when:
        ResponseEntity<List<Question>> response =
                testRestTemplate.exchange("/api/questions", HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });
        var data = response.getBody();

        // then:
        assertThat(data).first().usingRecursiveComparison().isEqualTo(sample);
    }

    @Test
    void shouldForbidAnonymousUsersFetchingQuestionById() {
        var response = testRestTemplate.exchange("/api/questions/123", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}

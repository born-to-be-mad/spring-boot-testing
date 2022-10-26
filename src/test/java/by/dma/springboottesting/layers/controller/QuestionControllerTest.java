package by.dma.springboottesting.layers.controller;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.service.QuestionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService service;

    @Test
    void shouldReturnOK() throws Exception {
        mockMvc
            .perform(get("/api/questions"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfQuestions() throws Exception {
        // given:
        Question sample = new Question();
        sample.setId(123L);
        sample.setSortOrder(2);
        sample.setName("What is your favorite social network?");
        sample.setCreatedAt(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                             ZoneId.of("Europe/Minsk")));
        Mockito.when(service.getAllQuestions())
               .thenReturn(List.of(sample));

        // when:
        var result = mockMvc.perform(get("/api/questions"));

        //then:
        result.andExpect(jsonPath("$.size()").value(1))
              .andExpect(jsonPath("$[0].id").value(123))
              .andExpect(jsonPath("$[0].name").value("What is your favorite social network?"))
              .andExpect(jsonPath("$[0].createdAt").value("2020-01-01T00:00:00+03:00"));
    }

    @Test
    void shouldForbidAnonymousUsersFetchingQuestionById() throws Exception {
        mockMvc
                .perform(get("/api/questions/123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldAllowAuthenticatedUsersToFetchQuestionById() throws Exception {
        // given:
        Question sample = new Question();
        sample.setId(123L);
        sample.setSortOrder(2);
        sample.setName("What is your favorite social network?");
        sample.setCreatedAt(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                             ZoneId.of("Europe/Minsk")));

        Mockito.when(service.getQuestionById(123L))
               .thenReturn(sample);

        mockMvc
                .perform(get("/api/questions/123"))
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.name").value("What is your favorite social network?"))
                .andExpect(jsonPath("$.createdAt").value("2020-01-01T00:00:00+03:00"));
    }
}

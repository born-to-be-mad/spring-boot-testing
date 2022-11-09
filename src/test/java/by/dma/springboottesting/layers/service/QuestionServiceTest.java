package by.dma.springboottesting.layers.service;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.repository.QuestionRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.10
 */
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    private QuestionService service;

    @Mock
    private QuestionRepository repository;

    @Mock
    private Random randomizer;

    @BeforeEach
    void setUp() {
        service = new QuestionService(repository, randomizer);
    }

    @Test
    void getRandomQuestion() {
        Mockito.when(repository.findAll())
                .thenReturn(List.of(new Question(), new Question(), new Question()));
        assertThat(service.getRandomQuestion()).isNotNull();
    }
}
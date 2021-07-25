package by.dma.springboottesting.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import by.dma.springboottesting.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldSaveAndRetrieveJpaEntity() {
        Question entityToStore = new Question();
        entityToStore.setName("mike");
        entityToStore.setCreatedAt(ZonedDateTime.now());

        Question result = testEntityManager.persistFlushFind(entityToStore);

        assertNotNull(result.getId());
    }

    @Test
    void shouldReturnQuestionThatJoinedTheEarliest() {

        repository.deleteAll();

        Question one = new Question("What is thread?", ZonedDateTime.now());
        Question two = new Question("What is spring boot?", ZonedDateTime.now().minusDays(42));
        Question three = new Question("What is the difference between JVM and JRE?", ZonedDateTime.now().minusMinutes(42));

        repository.saveAll(List.of(one, two, three));

        Question result = repository.getEarlyBird();

        assertEquals("What is spring boot?", result.getName());
    }
}

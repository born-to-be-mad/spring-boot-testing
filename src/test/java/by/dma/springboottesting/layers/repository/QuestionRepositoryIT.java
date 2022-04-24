package by.dma.springboottesting.layers.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import by.dma.springboottesting.layers.repository.QuestionRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties ={
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:13:///springboot"})
class QuestionRepositoryIT {
    @Autowired
    private QuestionRepository repository;

    @Test
    @Sql("/scripts/INIT_TEST_QUESTIONS.sql")
    void shouldReturnCorrectEarlyBird() {
        var earlyBird = repository.getEarlyBird();
        assertThat(earlyBird).isNotNull();
        assertThat(earlyBird.getId()).isEqualTo(123);
    }
}

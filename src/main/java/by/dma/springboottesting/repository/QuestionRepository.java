package by.dma.springboottesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import by.dma.springboottesting.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value =
            "SELECT * " +
                    "FROM question " +
                    "ORDER BY created_at ASC " +
                    "LIMIT 1", nativeQuery = true)
    Question getEarlyBird();
}

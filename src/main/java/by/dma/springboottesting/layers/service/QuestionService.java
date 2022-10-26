package by.dma.springboottesting.layers.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import by.dma.springboottesting.domains.customer.CustomerNotFoundException;
import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository repository;

    private final Random randomizer;

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(Long id) {
        return repository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public Question getRandomQuestion() {
        final var all = repository.findAll();
        final var randomIndex = randomizer.nextInt(all.size());
        return Optional.ofNullable(all.get(randomIndex)).orElseThrow(CustomerNotFoundException::new);
    }
}

package by.dma.springboottesting.layers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.dma.springboottesting.layers.model.Question;
import by.dma.springboottesting.layers.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Question> getAll() {
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getById(@PathVariable("id") Long id) {
        return service.getQuestionById(id);
    }

    @GetMapping("/random")
    public Question getRandom() {
        return service.getRandomQuestion();
    }
}

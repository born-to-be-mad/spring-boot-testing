package by.dma.springboottesting.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import by.dma.springboottesting.domain.Question;

public class QuestionDto {
    @JsonProperty("questionName")
    private final String name;

    @JsonProperty("available_since")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm")
    private final ZonedDateTime createdAt;

    public QuestionDto(Question question) {
        this.name = question.getName();
        this.createdAt = question.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

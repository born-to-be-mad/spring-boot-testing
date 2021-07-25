package by.dma.springboottesting.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import by.dma.springboottesting.domain.Question;

public class QuestionDto {
    @JsonProperty("questionText")
    private final String text;

    @JsonProperty("available_since")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm")
    private final ZonedDateTime createdAt;

    public QuestionDto(Question question) {
        this.text = question.getName();
        this.createdAt = question.getCreatedAt();
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

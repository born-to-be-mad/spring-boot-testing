package by.dma.springboottesting.layers.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.dma.springboottesting.layers.dto.QuestionDto;
import by.dma.springboottesting.layers.model.Question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
class QuestionDtoTest {
    @Autowired
    private JacksonTester<QuestionDto> jacksonTester;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSerializeWithCustomAttributeNamesAndDateFormat() throws Exception {
        // given:
        assertNotNull(objectMapper);
        QuestionDto dto = new QuestionDto(
                new Question("To be or not to be?",
                             ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Minsk"))));
        // when:
        JsonContent<QuestionDto> result = jacksonTester.write(dto);

        // then:
        assertThat(result).hasJsonPathStringValue("@.available_since");
        assertThat(result).hasJsonPathStringValue("@.questionText");
        assertThat(result).extractingJsonPathStringValue("@.available_since").isEqualTo("01-01 00:00");
        assertThat(result).doesNotHaveJsonPath("$.id");
    }
}

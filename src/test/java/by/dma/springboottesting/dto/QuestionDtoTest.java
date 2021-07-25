package by.dma.springboottesting.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import by.dma.springboottesting.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class QuestionDtoTest {
    @Autowired
    private JacksonTester<QuestionDto> jacksonTester;

    @Test
    void shouldSerializeWithCustomAttributeNamesAndDateFormat() throws Exception {
        // given:
        QuestionDto dto = new QuestionDto(
                new Question("To be or not to be?",
                             ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Minsk"))));

        JsonContent<QuestionDto> result = jacksonTester.write(dto);

        assertThat(result).hasJsonPathStringValue("@.available_since");
        assertThat(result).hasJsonPathStringValue("@.questionText");
        assertThat(result).extractingJsonPathStringValue("@.available_since").isEqualTo("01-01 00:00");
    }
}

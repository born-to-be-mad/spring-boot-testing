package by.dma.springboottesting.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasicSelenideUITest {

    @LocalServerPort
    private Integer port;

    @RegisterExtension
    public static ScreenShooterExtension screenShooterExtension = new ScreenShooterExtension()
            .to("target/selenide-screenshots");

    @AfterAll
    static void cleanUp() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void shouldAccessSpringInitializerAndGenerateMavenProjectWithJava11() {
        assertThat(port).isPositive();

        Selenide.open("https://start.spring.io/");
        screenshot("spring-io-initial-view");

        assertThat(Selenide.title()).isEqualTo("Spring Initializr");

/*         var element = $("label.label").val("Project");

        $(By.id("lname")).val("Mike");
        $(By.id("fname")).val("Duke");

        $(By.id("submit")).click(); */

        screenshot("spring-io-final-view");
    }
}

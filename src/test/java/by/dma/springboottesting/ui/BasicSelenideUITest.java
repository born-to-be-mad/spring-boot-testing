package by.dma.springboottesting.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.ScreenShooterExtension;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

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

    @RegisterExtension
    public static ScreenShooterExtension screenShooterExtension = new ScreenShooterExtension()
            .to("target/selenide-screenshots");

    @BeforeAll
    static void configureChromeDriver(@Autowired Environment environment) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--headless",
                "--disable-gpu",
                "--disable-extensions");
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = chromeOptions;

        Configuration.reportsFolder = "target/selenide-screenshots";
        Configuration.timeout = 3_000;

        // Local server configuration
        Integer port = environment.getProperty("local.server.port", Integer.class);
        assertThat(port).isPositive();
        // Configuration.baseUrl = "http://localhost:" + port;
    }

    @Test
    void shouldAccessSpringInitializerAndChangeProjectSettings() {
        Selenide.open("https://start.spring.io/");
        screenshot("spring-io-initial-view");

        assertThat(Selenide.title()).isEqualTo("Spring Initializr");

        var project = getRadioGroup("Project", "Maven");
        assertThat(project.getKey().getText()).isEqualTo("Gradle - Groovy");
        project.getValue().click();

        var language = getRadioGroup("Language", "Kotlin");
        assertThat(language.getKey().getText()).isEqualTo("Java");
        language.getValue().click();

        var spring = getRadioGroup("Spring Boot", "2.7.6");
        assertThat(spring.getKey().getText()).isEqualTo("3.0.0");
        spring.getValue().click();

        var packaging = getLabeledGroup("input-packaging", "Jar");
        assertThat(packaging.getKey().getText()).isEqualTo("Jar");

        var java = getLabeledGroup("input-java", "11");
        assertThat(java.getKey().getText()).isEqualTo("17");
        java.getValue().click();

        screenshot("spring-io-final-view");
    }

    private static Pair<SelenideElement, SelenideElement> getLabeledGroup(String labelFor, String desiredElementName) {
        var parentContainer = Selenide
                .$(String.format("label[for=%s]", labelFor))
                .parent();
        var currentElement = parentContainer.$(".radio.checked");
        var desiredElement = parentContainer
                .$$(".radio")
                .findBy(Condition.text(desiredElementName));
        return Pair.of(currentElement, desiredElement);
    }

    private static Pair<SelenideElement, SelenideElement> getRadioGroup(String groupName, String desiredElementName) {
        var parentContainer = Selenide
                .$$(By.className("label"))
                .findBy(Condition.text(groupName))
                .parent();
        var currentElement = parentContainer.$(".radio.checked");
        var desiredElement = parentContainer
                .$$(".radio")
                .findBy(Condition.text(desiredElementName));
        return Pair.of(currentElement, desiredElement);
    }
}

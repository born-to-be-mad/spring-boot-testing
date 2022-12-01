package by.dma.springboottesting.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The usage of the Testcontainers WebDriver module run the web test inside a Docker container.
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdvancedSelenideIT {

    @Container
    static BrowserWebDriverContainer<?> webDriverContainer =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(
                            new ChromeOptions()
                            //.addArguments("--no-sandbox")
                            //.addArguments("--disable-dev-shm-usage")
                    );

    @BeforeAll
    static void beforeAll(@Autowired Environment environment) {
        var remoteWebDriver = webDriverContainer.getWebDriver();
        WebDriverRunner.setWebDriver(remoteWebDriver);

        Configuration.timeout = 2_000;
        Configuration.reportsFolder = "target/selenide-screenshots";
        Integer port = environment.getProperty("local.server.port", Integer.class);
        // Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d", port);
        Configuration.baseUrl = String.format("http://172.17.0.1:%d", port);
    }

    @AfterAll
    static void cleanUp() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void shouldAccessSpringInitializerAndGenerateMavenProjectWithJava11() {
        Selenide.open("https://start.spring.io/");
        screenshot("spring-io-initial-view");

        assertThat(Selenide.title()).isEqualTo("Spring Initializr");

        screenshot("spring-io-final-view");
    }
}

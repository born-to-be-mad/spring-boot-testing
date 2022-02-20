package by.dma.springboottesting;


import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Use the GenericContainer class from Testcontainers to start a custom Docker image with a health check strategy.
 */
@Testcontainers
class TestContainersAdvancedTests {

/*  @Container
  static GenericContainer<?> container =
      new GenericContainer<>(DockerImageName.parse("jboss/keycloak:16.1.1"))
          .withExposedPorts(8180)
          //.withStartupTimeout(Duration.ofSeconds(30))
          .waitingFor(Wait.forHttp("/auth").forStatusCode(HttpStatus.OK.value())
                          .withStartupTimeout(Duration.of(7, ChronoUnit.MINUTES))
          );*/

  @Container
  static GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("nginx:1.9.4"))
      .withExposedPorts(80)
      .waitingFor(Wait.forHttp("/"));

  @Test
  void shouldStartCustomDockerContainer() {
    assertTrue(container.isRunning());
  }
}

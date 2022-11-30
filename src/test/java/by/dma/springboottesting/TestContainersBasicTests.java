package by.dma.springboottesting;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testcontainers disrupted the way we write our integration tests by providing external components as Docker containers.
 */
@Testcontainers
class TestContainersBasicTests {

    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
            .withDatabaseName("test")
            .withUsername("spring-boot")
            .withPassword("spring-boot");

    // Spring boot > 2.2.6
/*    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }*/

    @Test
    void shouldStartPostgreSQLDatabase() {
        assertTrue(container.isRunning());
        System.out.println("Context loads!");
    }
}

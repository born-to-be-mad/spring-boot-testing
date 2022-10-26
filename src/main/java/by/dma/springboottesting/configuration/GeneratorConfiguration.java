package by.dma.springboottesting.configuration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.10
 */
@Configuration
public class GeneratorConfiguration {

    @Bean
    public Random randomizer() {
        return ThreadLocalRandom.current();
    }
}

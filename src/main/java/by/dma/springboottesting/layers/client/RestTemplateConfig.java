package by.dma.springboottesting.layers.client;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Prepare different RestTemplate beans for our application inside a @Configuration class.
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate reqresRestTemplateClient(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri("https://reqres.in/")
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
    }

    @Bean
    public RestTemplate todoRestTemplateClient(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri("https://jsonplaceholder.typicode.com")
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
    }
}

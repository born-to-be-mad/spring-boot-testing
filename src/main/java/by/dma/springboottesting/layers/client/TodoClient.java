package by.dma.springboottesting.layers.client;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TodoClient {
    private final RestTemplate restTemplate;

    public TodoClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri("https://jsonplaceholder.typicode.com")
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
    }

    public List<Todo> fetchAllTodos() {
        return restTemplate
                .exchange("/todos", HttpMethod.GET, null, new ParameterizedTypeReference<List<Todo>>() {})
                .getBody();
    }
}

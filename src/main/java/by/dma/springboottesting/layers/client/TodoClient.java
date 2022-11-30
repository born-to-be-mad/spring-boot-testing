package by.dma.springboottesting.layers.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TodoClient {

    private final RestTemplate todoRestTemplateClient;

    public List<Todo> fetchAllTodos() {
        return todoRestTemplateClient
                .exchange("/todos", HttpMethod.GET, null, new ParameterizedTypeReference<List<Todo>>() {

                })
                .getBody();
    }
}

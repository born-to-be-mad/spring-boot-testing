package by.dma.springboottesting.layers.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@Component
@AllArgsConstructor
public class UserClient {

    private final RestTemplate reqresRestTemplateClient;

    public User getSingleUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return reqresRestTemplateClient
                .exchange("/api/users/{id}", HttpMethod.GET, requestEntity, User.class, id)
                .getBody();

    }
}

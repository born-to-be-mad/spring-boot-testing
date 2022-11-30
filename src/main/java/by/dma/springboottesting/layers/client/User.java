package by.dma.springboottesting.layers.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@Data
public class User {

    private final long id;

    @JsonProperty("janet.weaver@reqres.in")
    private final String email;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    private final String avatar;
}

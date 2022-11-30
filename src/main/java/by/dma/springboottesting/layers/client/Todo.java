package by.dma.springboottesting.layers.client;

import lombok.Data;

@Data
public class Todo {
    private final Long userId;
    private final Long id;
    private final String title;
    private final boolean completed;
}

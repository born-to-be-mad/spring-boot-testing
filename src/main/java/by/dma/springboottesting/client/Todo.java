package by.dma.springboottesting.client;

import lombok.Data;

@Data
public class Todo {
    private Long userId;
    private Long id;
    private String title;
    private boolean completed;
}

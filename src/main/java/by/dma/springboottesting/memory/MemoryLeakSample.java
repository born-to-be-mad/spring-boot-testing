package by.dma.springboottesting.memory;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Memory Leak in production.
 * How to reproduce:
 * `ab -n 10000 http://localhost:8080/api/memory` to
 *
 * @author dzmitry.marudau
 * @since 2022.11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memory")
public class MemoryLeakSample {
    private final LeakComponent leakComponent;

    @GetMapping
    void doIt() {
        String randomString = RandomStringUtils.randomAlphabetic(10000);
        leakComponent.doLeak(randomString);
        LeakClass.doLeak(randomString);
    }
}

@Component
class LeakComponent {
    private final Set<String> leak = new HashSet<>();

    void doLeak(String s) {
        leak.add(s);
    }
}
class LeakClass {
    private static final Set<String> leak = new HashSet<>();

    static void doLeak(String s) {
        leak.add(s);
    }
}
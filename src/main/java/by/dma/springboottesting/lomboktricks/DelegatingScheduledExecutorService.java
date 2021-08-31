package by.dma.springboottesting.lomboktricks;

import java.util.concurrent.ScheduledExecutorService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class DelegatingScheduledExecutorService implements ScheduledExecutorService {

  @Delegate
  private final ScheduledExecutorService delegate;

}

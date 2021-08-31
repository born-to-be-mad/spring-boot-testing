package by.dma.springboottesting.lomboktricks;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WrappingScheduledExecutorService extends DelegatingScheduledExecutorService {
  public WrappingScheduledExecutorService(ScheduledExecutorService delegate) {
    super(delegate);
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    command = wrap(command);
    return super.schedule(command, delay, unit);
  }

  private Runnable wrap(Runnable command) {
    System.out.printf("Wrapping command....");
    return command;
  }
}

package by.dma.springboottesting.lomboktricks;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class DelegatingCollection implements Collection<String> {

  @Delegate
  private final Collection<String> delegate;

}

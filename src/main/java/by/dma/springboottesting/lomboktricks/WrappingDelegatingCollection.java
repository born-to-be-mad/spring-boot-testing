package by.dma.springboottesting.lomboktricks;

import java.util.Collection;

public class WrappingDelegatingCollection extends DelegatingCollection {

  long counter = 0L;

  public WrappingDelegatingCollection(Collection<String> delegate) {
    super(delegate);
  }

  @Override
  public boolean add(String e) {
    counter++;
    return super.add(e);
  }

  @Override
  public boolean addAll(Collection<? extends String> col) {
    counter += col.size();
    return super.addAll(col);
  }

  public long getCounter() {
    return counter;
  }
}

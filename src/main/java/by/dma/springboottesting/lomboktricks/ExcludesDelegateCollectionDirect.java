package by.dma.springboottesting.lomboktricks;

import java.util.ArrayList;
import java.util.Collection;

import lombok.experimental.Delegate;

public class ExcludesDelegateCollectionDirect {
  long counter = 0L;

  //This interface defines which methods Lombok should not delegate.
  private interface Add {
    boolean add(String x);

    boolean addAll(Collection<? extends String> x);
  }

  // All methods of the Collection interface will be delegated to this field, except the excludes
  @Delegate(excludes = Add.class)
  private final Collection<String> collection = new ArrayList<>();

  public boolean add(String item) {
    counter++;
    return collection.add(item);
  }

  public boolean addAll(Collection<? extends String> col) {
    counter += col.size();
    return collection.addAll(col);
  }

  public long getCounter() {
    return counter;
  }
}

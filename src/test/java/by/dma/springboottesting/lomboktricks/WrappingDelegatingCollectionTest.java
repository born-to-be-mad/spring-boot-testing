package by.dma.springboottesting.lomboktricks;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WrappingDelegatingCollectionTest {
  @Test
  void testDelegate() {
    var delegate = new WrappingDelegatingCollection(new ArrayList<>());
    delegate.add("aaa");
    delegate.addAll(List.of("bbb", "ccc", "ddd"));
    Assertions
        .assertThat(4)
        .isEqualTo(delegate.getCounter());
  }
}

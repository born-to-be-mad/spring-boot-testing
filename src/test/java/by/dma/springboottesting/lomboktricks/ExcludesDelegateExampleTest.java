package by.dma.springboottesting.lomboktricks;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ExcludesDelegateExampleTest {

  @Test
  void testDelegate() {
    var delegate = new ExcludesDelegateCollectionDirect();
    delegate.add("aaa");
    delegate.addAll(List.of("bbb", "ccc", "ddd"));
    Assertions
        .assertThat(4)
        .isEqualTo(delegate.getCounter());
  }
}

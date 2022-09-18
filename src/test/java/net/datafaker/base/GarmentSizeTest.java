package net.datafaker.base;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GarmentSizeTest extends AbstractBaseFakerTest {

  @Test
  public void size() {
    assertThat(faker.garmentSize().size()).isNotEmpty();
  }
}

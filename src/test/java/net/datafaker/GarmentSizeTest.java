package net.datafaker;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GarmentSizeTest extends AbstractFakerTest {

  @Test
  public void size() {
    assertThat(faker.garmentSize().size()).isNotEmpty();
  }
}

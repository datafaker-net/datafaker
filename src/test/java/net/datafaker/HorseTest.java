package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HorseTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.horse().name()).isNotEmpty();
    }

    @Test
    public void breed() {
        assertThat(faker.horse().breed()).isNotEmpty();
    }

}

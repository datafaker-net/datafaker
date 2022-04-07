package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AustraliaTest extends AbstractFakerTest {

    @Test
    public void locations() {
        assertThat(faker.australia().locations()).isNotEmpty();
    }

    @Test
    public void animals() {
        assertThat(faker.australia().animals()).isNotEmpty();
    }

    @Test
    public void states() {
        assertThat(faker.australia().states()).isNotEmpty();
    }
}

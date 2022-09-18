package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HobbyTest extends AbstractBaseFakerTest {

    @Test
    void activity() {
        assertThat(faker.hobby().activity()).isNotEmpty();
    }
}

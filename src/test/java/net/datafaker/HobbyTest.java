package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HobbyTest extends AbstractFakerTest {

    @Test
    void activity() {
        assertThat(faker.hobby().activity()).isNotEmpty();
    }
}

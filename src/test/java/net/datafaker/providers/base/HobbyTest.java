package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HobbyTest extends BaseFakerTest<BaseFaker> {

    @Test
    void activity() {
        assertThat(faker.hobby().activity()).isNotEmpty();
    }
}

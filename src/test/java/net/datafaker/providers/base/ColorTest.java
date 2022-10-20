package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColorTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.color().name()).matches("(\\w+ ?){1,2}");
    }

    @Test
    void hex() {
        assertThat(faker.color().hex()).matches("^#[0-9A-F]{6}$");
    }

    @Test
    void hexNoHashSign() {
        assertThat(faker.color().hex(false)).matches("^[0-9A-F]{6}$");
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColorTest extends AbstractFakerTest {

    @Test
    void testName() {
        assertThat(faker.color().name()).matches("(\\w+ ?){1,2}");
    }

    @Test
    void testHex() {
        assertThat(faker.color().hex()).matches("^#[0-9A-F]{6}$");
    }

    @Test
    void testHexNoHashSign() {
        assertThat(faker.color().hex(false)).matches("^[0-9A-F]{6}$");
    }
}

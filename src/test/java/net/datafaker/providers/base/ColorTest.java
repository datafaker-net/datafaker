package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColorTest extends BaseFakerTest {

    private final Color color = faker.color();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(color::name, "color.name", "(\\w+ ?){1,2}"));
    }

    @Test
    void testHex() {
        assertThat(color.hex()).matches("^#[0-9A-F]{6}$");
    }

    @Test
    void testHexNoHashSign() {
        assertThat(color.hex(false)).matches("^[0-9A-F]{6}$");
    }
}

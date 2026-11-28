package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

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

    @ParameterizedTest
    @ValueSource(strings = {"zh-CN", "zh-TW"})
    void chineseColorNames(String locale) {
        BaseFaker chineseFaker = new BaseFaker(Locale.forLanguageTag(locale));
        for (int i = 0; i < 50; i++) {
            assertThat(chineseFaker.color().name()).matches("\\p{IsHan}{1,5}");
        }
    }
}

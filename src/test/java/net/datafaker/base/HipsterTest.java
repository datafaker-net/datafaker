package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HipsterTest extends AbstractBaseFakerTest {

    @Test
    void word() {
        assertThat(faker.hipster().word()).matches("^([\\w-+&']+ ?)+$");
    }
}

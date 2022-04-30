package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HipsterTest extends AbstractFakerTest {

    @Test
    void word() {
        assertThat(faker.hipster().word()).matches("^([\\w-+&']+ ?)+$");
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoinTest extends AbstractFakerTest {

    @Test
    void coinFlip() {
        assertThat(faker.coin().flip()).matches("\\w+");
    }
}

package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StockTest extends BaseFakerTest {

    @Test
    void testNasdaq() {
        assertThat(faker.stock().nsdqSymbol()).isNotEmpty();
    }

    @Test
    void testNYSE() {
        assertThat(faker.stock().nyseSymbol()).isNotEmpty();
    }

}

package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StockTest extends BaseFakerTest<BaseFaker> {

    @Test
    void nasdaq() {
        assertThat(faker.stock().nsdqSymbol()).isNotEmpty();
    }

    @Test
    void nYSE() {
        assertThat(faker.stock().nyseSymbol()).isNotEmpty();
    }

}

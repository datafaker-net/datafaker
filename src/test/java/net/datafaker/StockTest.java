package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockTest extends AbstractFakerTest {

    @Test
    public void testNasdaq() {
        assertThat(faker.stock().nsdqSymbol()).isNotEmpty();
    }

    @Test
    public void testNYSE() {
        assertThat(faker.stock().nyseSymbol()).isNotEmpty();
    }

}

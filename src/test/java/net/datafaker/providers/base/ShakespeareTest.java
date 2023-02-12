package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShakespeareTest extends BaseFakerTest<BaseFaker> {

    private final Shakespeare shakespeare = faker.shakespeare();

    @Test
    void testHamletQuote() {
        assertThat(shakespeare.hamletQuote()).isNotEmpty();
    }

    @Test
    void testAsYouLikeItQuote() {
        assertThat(shakespeare.asYouLikeItQuote()).isNotEmpty();
    }

    @Test
    void testKingRichardIIIQuote() {
        assertThat(shakespeare.kingRichardIIIQuote()).isNotEmpty();
    }

    @Test
    void testRomeoAndJulietQuote() {
        assertThat(shakespeare.romeoAndJulietQuote()).isNotEmpty();
    }
}

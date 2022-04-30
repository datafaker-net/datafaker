package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShakespeareTest extends AbstractFakerTest {

    @Test
    void testHamletQuote() {
        assertThat(faker.shakespeare().hamletQuote()).isNotEmpty();
    }

    @Test
    void testAsYouLikeItQuote() {
        assertThat(faker.shakespeare().asYouLikeItQuote()).isNotEmpty();
    }

    @Test
    void testKingRichardIIIQuote() {
        assertThat(faker.shakespeare().kingRichardIIIQuote()).isNotEmpty();
    }

    @Test
    void testRomeoAndJulietQuote() {
        assertThat(faker.shakespeare().romeoAndJulietQuote()).isNotEmpty();
    }
}

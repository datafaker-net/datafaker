package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShakespeareTest extends BaseFakerTest<BaseFaker> {

    @Test
    void hamletQuote() {
        assertThat(faker.shakespeare().hamletQuote()).isNotEmpty();
    }

    @Test
    void asYouLikeItQuote() {
        assertThat(faker.shakespeare().asYouLikeItQuote()).isNotEmpty();
    }

    @Test
    void kingRichardIIIQuote() {
        assertThat(faker.shakespeare().kingRichardIIIQuote()).isNotEmpty();
    }

    @Test
    void romeoAndJulietQuote() {
        assertThat(faker.shakespeare().romeoAndJulietQuote()).isNotEmpty();
    }
}

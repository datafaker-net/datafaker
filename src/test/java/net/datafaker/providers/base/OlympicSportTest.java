package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OlympicSportTest extends net.datafaker.AbstractFakerTest {

    @Test
    void summerOlympics() {
        assertThat(faker.olympicSport().summerOlympics()).isNotEmpty();
    }

    @Test
    void winterOlympics() {
        assertThat(faker.olympicSport().winterOlympics()).isNotEmpty();
    }

    @Test
    void summerParalympics() {
        assertThat(faker.olympicSport().summerParalympics()).isNotEmpty();
    }

    @Test
    void winterParalympics() {
        assertThat(faker.olympicSport().winterParalympics()).isNotEmpty();
    }

    @Test
    void ancientOlympics() {
        assertThat(faker.olympicSport().ancientOlympics()).isNotEmpty();
    }

    @Test
    void unusual() {
        assertThat(faker.olympicSport().unusual()).isNotEmpty();
    }

}

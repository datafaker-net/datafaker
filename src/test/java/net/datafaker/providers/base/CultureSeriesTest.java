package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CultureSeriesTest extends net.datafaker.AbstractFakerTest {

    @Test
    void books() {
        assertThat(faker.cultureSeries().books()).isNotEmpty();
    }

    @Test
    void cultureShips() {
        assertThat(faker.cultureSeries().cultureShips()).isNotEmpty();
    }

    @Test
    void cultureShipClasses() {
        assertThat(faker.cultureSeries().cultureShipClasses()).isNotEmpty();
    }

    @Test
    void cultureShipClassAbvs() {
        assertThat(faker.cultureSeries().cultureShipClassAbvs()).isNotEmpty();
    }

    @Test
    void civs() {
        assertThat(faker.cultureSeries().civs()).isNotEmpty();
    }

    @Test
    void planets() {
        assertThat(faker.cultureSeries().planets()).isNotEmpty();
    }

}

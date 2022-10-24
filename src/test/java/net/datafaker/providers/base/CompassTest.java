package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompassTest extends BaseFakerTest<BaseFaker> {

    @Test
    void azimuth() {
        assertThat(faker.compass().azimuth()).isNotEmpty();
    }

    @Test
    void directionWord() {
        assertThat(faker.compass().directionWord()).isNotEmpty();
    }

    @Test
    void directionAbbreviation() {
        assertThat(faker.compass().directionAbbreviation()).isNotEmpty();
    }

    @Test
    void cardinalDirectionWord() {
        assertThat(faker.compass().cardinalDirectionWord()).isNotEmpty();
    }

    @Test
    void cardinalDirectionAbbreviation() {
        assertThat(faker.compass().cardinalDirectionAbbreviation()).isNotEmpty();
    }

    @Test
    void cardinalDirectionAzimuth() {
        assertThat(faker.compass().cardinalDirectionAzimuth()).isNotEmpty();
    }

    @Test
    void ordinalDirectionWord() {
        assertThat(faker.compass().ordinalDirectionWord()).isNotEmpty();
    }

    @Test
    void ordinalDirectionAbbreviation() {
        assertThat(faker.compass().ordinalDirectionAbbreviation()).isNotEmpty();
    }

    @Test
    void ordinalDirectionAzimuth() {
        assertThat(faker.compass().ordinalDirectionAzimuth()).isNotEmpty();
    }

    @Test
    void halfWindDirectionWord() {
        assertThat(faker.compass().halfWindDirectionWord()).isNotEmpty();
    }

    @Test
    void halfWindDirectionAbbreviation() {
        assertThat(faker.compass().halfWindDirectionAbbreviation()).isNotEmpty();
    }

    @Test
    void halfWindDirectionAzimuth() {
        assertThat(faker.compass().halfWindDirectionAzimuth()).isNotEmpty();
    }

    @Test
    void quarterWindDirectionWord() {
        assertThat(faker.compass().quarterWindDirectionWord()).isNotEmpty();
    }

    @Test
    void quarterWindDirectionAbbreviation() {
        assertThat(faker.compass().quarterWindDirectionAbbreviation()).isNotEmpty();
    }

    @Test
    void quarterWindDirectionAzimuth() {
        assertThat(faker.compass().quarterWindDirectionAzimuth()).isNotEmpty();
    }
}

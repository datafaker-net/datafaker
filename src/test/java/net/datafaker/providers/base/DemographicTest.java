package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DemographicTest extends BaseFakerTest<BaseFaker> {

    @Test
    void race() {
        assertThat(faker.demographic().race()).matches("(\\w+ ?)+");
    }

    @Test
    void educationalAttainment() {
        assertThat(faker.demographic().educationalAttainment()).matches("(?U)([\\w'-]+ ?)+");
    }

    @Test
    void demonym() {
        assertThat(faker.demographic().demonym()).matches("(?U)([\\w'-]+ ?)+");
    }

    @Test
    void maritalStatus() {
        assertThat(faker.demographic().maritalStatus()).matches("(\\w+ ?)+");
    }

    @Test
    void sex() {
        assertThat(faker.demographic().sex()).matches("\\w+");
    }
}

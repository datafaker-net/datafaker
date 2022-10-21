package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CannabisTest extends BaseFakerTest<BaseFaker> {

    @Test
    void strains() {
        assertThat(faker.cannabis().strains()).isNotEmpty();
    }

    @Test
    void cannabinoidAbbreviations() {
        assertThat(faker.cannabis().cannabinoidAbbreviations()).isNotEmpty();
    }

    @Test
    void cannabinoids() {
        assertThat(faker.cannabis().cannabinoids()).isNotEmpty();
    }

    @Test
    void terpenes() {
        assertThat(faker.cannabis().terpenes()).isNotEmpty();
    }

    @Test
    void medicalUses() {
        assertThat(faker.cannabis().medicalUses()).isNotEmpty();
    }

    @Test
    void healthBenefits() {
        assertThat(faker.cannabis().healthBenefits()).isNotEmpty();
    }

    @Test
    void categories() {
        assertThat(faker.cannabis().categories()).isNotEmpty();
    }

    @Test
    void types() {
        assertThat(faker.cannabis().types()).isNotEmpty();
    }

    @Test
    void buzzwords() {
        assertThat(faker.cannabis().buzzwords()).isNotEmpty();
    }

    @Test
    void brands() {
        assertThat(faker.cannabis().brands()).isNotEmpty();
    }
}

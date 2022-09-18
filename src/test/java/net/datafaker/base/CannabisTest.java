package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CannabisTest extends BaseFakerTest {
    @Test
    public void strains() {
        assertThat(faker.cannabis().strains()).isNotEmpty();
    }

    @Test
    public void cannabinoidAbbreviations() {
        assertThat(faker.cannabis().cannabinoidAbbreviations()).isNotEmpty();
    }

    @Test
    public void cannabinoids() {
        assertThat(faker.cannabis().cannabinoids()).isNotEmpty();
    }

    @Test
    public void terpenes() {
        assertThat(faker.cannabis().terpenes()).isNotEmpty();
    }

    @Test
    public void medicalUses() {
        assertThat(faker.cannabis().medicalUses()).isNotEmpty();
    }

    @Test
    public void healthBenefits() {
        assertThat(faker.cannabis().healthBenefits()).isNotEmpty();
    }

    @Test
    public void categories() {
        assertThat(faker.cannabis().categories()).isNotEmpty();
    }

    @Test
    public void types() {
        assertThat(faker.cannabis().types()).isNotEmpty();
    }

    @Test
    public void buzzwords() {
        assertThat(faker.cannabis().buzzwords()).isNotEmpty();
    }

    @Test
    public void brands() {
        assertThat(faker.cannabis().brands()).isNotEmpty();
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MilitaryTest extends AbstractFakerTest {

    @Test
    public void armyRank() {
        assertThat(faker.military().armyRank()).isNotEmpty();
    }

    @Test
    public void marinesRank() {
        assertThat(faker.military().marinesRank()).isNotEmpty();
    }

    @Test
    public void navyRank() {
        assertThat(faker.military().navyRank()).isNotEmpty();
    }

    @Test
    public void airForceRank() {
        assertThat(faker.military().airForceRank()).isNotEmpty();
    }

    @Test
    public void dodPaygrade() {
        assertThat(faker.military().dodPaygrade()).isNotEmpty();
    }

}

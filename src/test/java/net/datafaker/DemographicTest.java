package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DemographicTest extends AbstractFakerTest {

    @Test
    public void race() {
        assertThat(faker.demographic().race()).matches("(\\w+ ?)+");
    }

    @Test
    public void educationalAttainment() {
        assertThat(faker.demographic().educationalAttainment()).matches("(?U)([\\w'-]+ ?)+");
    }

    @Test
    public void demonym() {
        assertThat(faker.demographic().demonym()).matches("(?U)([\\w'-]+ ?)+");
    }

    @Test
    public void maritalStatus() {
        assertThat(faker.demographic().maritalStatus()).matches("(\\w+ ?)+");
    }

    @Test
    public void sex() {
        assertThat(faker.demographic().sex()).matches("\\w+");
    }
}

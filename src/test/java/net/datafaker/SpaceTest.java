package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest extends AbstractFakerTest {

    @Test
    void planet() {
        assertThat(faker.space().planet()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void moon() {
        assertThat(faker.space().moon()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void galaxy() {
        assertThat(faker.space().galaxy()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void nebula() {
        assertThat(faker.space().nebula()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void starCluster() {
        assertThat(faker.space().starCluster()).matches("(\\w+[ -]?){1,3}");
    }

    @Test
    void constellation() {
        assertThat(faker.space().constellation()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void star() {
        assertThat(faker.space().star()).matches("(\\w+[ -]?){2,3}");
    }

    @Test
    void agency() {
        assertThat(faker.space().agency()).matches("(\\w+ ?){2,5}");
    }

    @Test
    void agencyAbbreviation() {
        assertThat(faker.space().agencyAbbreviation()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void nasaSpaceCraft() {
        assertThat(faker.space().nasaSpaceCraft()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void company() {
        assertThat(faker.space().company()).matches("((\\w|')+ ?){2,4}");
    }

    @Test
    void distanceMeasurement() {
        assertThat(faker.space().distanceMeasurement()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void meteorite() {
        assertThat(faker.space().meteorite()).matches("(?U)([\\w()]+[ -–]?){1,4}");
    }
}

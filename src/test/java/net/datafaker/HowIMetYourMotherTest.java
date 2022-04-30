package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HowIMetYourMotherTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.howIMetYourMother().character()).matches("^(\\w+\\.?\\s?)+$");
    }

    @Test
    void catchPhrase() {
        assertThat(faker.howIMetYourMother().catchPhrase()).isNotEmpty();
    }

    @Test
    void highFive() {
        assertThat(faker.howIMetYourMother().highFive()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    void quote() {
        assertThat(faker.howIMetYourMother().quote()).isNotEmpty();
    }
}

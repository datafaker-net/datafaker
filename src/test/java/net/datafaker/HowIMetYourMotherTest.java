package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HowIMetYourMotherTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.howIMetYourMother().character()).matches("^(\\w+\\.?\\s?)+$");
    }

    @Test
    public void catchPhrase() {
        assertThat(faker.howIMetYourMother().catchPhrase()).isNotEmpty();
    }

    @Test
    public void highFive() {
        assertThat(faker.howIMetYourMother().highFive()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    public void quote() {
        assertThat(faker.howIMetYourMother().quote()).isNotEmpty();
    }
}

package net.datafaker.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalTest extends AbstractBaseFakerTest {

    @Test
    void name() {
        assertThat(faker.animal().name()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(100)
    void scientificName() {
        assertThat(faker.animal().scientificName()).matches("[A-Z][a-z]+ [a-z]+");
    }

    @RepeatedTest(100)
    void genus() {
        assertThat(faker.animal().genus()).matches("[A-Z][a-z]+");
    }

    @RepeatedTest(100)
    void species() {
        assertThat(faker.animal().species()).matches("[a-z]+");
    }
}

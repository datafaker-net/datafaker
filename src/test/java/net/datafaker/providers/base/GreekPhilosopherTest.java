package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class GreekPhilosopherTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void name() {
        assertThat(faker.greekPhilosopher().name()).matches("^[a-zA-Z ]+$");
    }

    @RepeatedTest(10)
    void quote() {
        assertThat(faker.greekPhilosopher().quote()).matches("^[a-zA-Z ,.']+$");
    }
}

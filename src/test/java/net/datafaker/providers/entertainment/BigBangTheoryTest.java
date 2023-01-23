package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BigBangTheoryTest extends EntertainmentFakerTest {

    @RepeatedTest(50)
    void testCharacter() {
        assertThat(faker.bigBangTheory().character()).matches("^[A-Z][a-zA-Z .]+$");
    }

    @RepeatedTest(50)
    void testQuote() {
        assertThat(faker.bigBangTheory().quote()).matches("^[A-Z][a-zA-Z .’'!,?]+$");
    }
}

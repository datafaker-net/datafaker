package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class GreekPhilosopherTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void testName() {
        assertThat(faker.greekPhilosopher().name()).matches("^[a-zA-Z ]+$");
    }

    @RepeatedTest(10)
    void testQuote() {
        assertThat(faker.greekPhilosopher().quote()).matches("^[a-zA-Z ,.']+$");
    }
}

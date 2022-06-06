package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class DepartedTest extends AbstractFakerTest {

    @RepeatedTest(100)
    void testActor() {
        assertThat(faker.departed().actor()).matches("^[a-zA-Z ']+$");
    }

    @RepeatedTest(100)
    void testCharacter() {
        assertThat(faker.departed().character()).matches("^[a-zA-Z ]+$");
    }

    @RepeatedTest(100)
    void testQuote() {
        assertThat(faker.departed().quote()).matches("^[a-zA-Z '.?!,]+$");
    }
}

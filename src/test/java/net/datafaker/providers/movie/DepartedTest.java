package net.datafaker.providers.movie;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class DepartedTest extends MovieFakerTest {

    @RepeatedTest(100)
    void actor() {
        assertThat(faker.departed().actor()).matches("^[a-zA-Z ']+$");
    }

    @RepeatedTest(100)
    void character() {
        assertThat(faker.departed().character()).matches("^[a-zA-Z ]+$");
    }

    @RepeatedTest(100)
    void quote() {
        assertThat(faker.departed().quote()).matches("^[a-zA-Z '.?!,]+$");
    }
}

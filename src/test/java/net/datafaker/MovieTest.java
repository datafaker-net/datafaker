package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTest extends AbstractFakerTest {

    @RepeatedTest(50)
    void testName() {
        assertThat(faker.movie().quote()).matches("^[a-zA-Z ,'’.?]+$");
    }
}

package net.datafaker.providers.movie;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTest extends MovieFakerTest {

    @RepeatedTest(50)
    void testQuote() {
        assertThat(faker.movie().quote()).matches("^[a-zA-Z ,'’.?]+$");
    }
}

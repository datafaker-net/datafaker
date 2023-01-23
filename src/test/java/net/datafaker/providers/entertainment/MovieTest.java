package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTest extends EntertainmentFakerTest {

    @RepeatedTest(50)
    void testQuote() {
        assertThat(faker.movie().quote()).matches("^[a-zA-Z ,'’.?]+$");
    }
}

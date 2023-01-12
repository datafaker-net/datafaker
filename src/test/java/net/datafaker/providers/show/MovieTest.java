package net.datafaker.providers.show;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTest extends ShowFakerTest {

    @RepeatedTest(50)
    void testQuote() {
        assertThat(faker.movie().quote()).matches("^[a-zA-Z ,'’.?]+$");
    }
}

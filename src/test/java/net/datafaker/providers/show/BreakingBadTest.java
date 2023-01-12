package net.datafaker.providers.show;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BreakingBadTest extends ShowFakerTest {

    @RepeatedTest(10)
    void character() {
        assertThat(faker.breakingBad().character()).matches("[\\p{L}A-Za-z0-9 .\\-;']+");
    }

    @RepeatedTest(10)
    void episodes() {
        assertThat(faker.breakingBad().episode()).isNotEmpty();
    }
}

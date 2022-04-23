package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class BreakingBadTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void character() {
        assertThat(faker.breakingBad().character()).matches("[\\p{L}A-Za-z0-9 .\\-;']+");
    }

    @RepeatedTest(10)
    public void episodes() {
        assertThat(faker.breakingBad().episode()).isNotEmpty();
    }
}

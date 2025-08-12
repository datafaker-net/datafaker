package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BoolTest {

    private final Faker faker = new Faker();

    @RepeatedTest(10)
    void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }
}

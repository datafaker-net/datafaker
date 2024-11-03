package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BoolTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }
}

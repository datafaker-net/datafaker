package net.datafaker.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BoolTest extends AbstractBaseFakerTest {

    @RepeatedTest(100)
    void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }
}

package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }
}

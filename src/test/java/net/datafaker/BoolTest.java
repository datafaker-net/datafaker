package net.datafaker;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

public class BoolTest extends AbstractFakerTest {

    @Test
    public void testBool() {
        for (int i = 0; i < 100; i++) {
            assertThat(faker.bool().bool(), is(oneOf(true, false)));
        }
    }
}

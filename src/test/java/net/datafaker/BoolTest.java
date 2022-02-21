package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

public class BoolTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void testBool() {
        assertThat(faker.bool().bool(), is(oneOf(true, false)));
    }
}

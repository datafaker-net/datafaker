package net.datafaker;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;

public class MatzTest extends AbstractFakerTest {

    @Test
    public void quote() {
        assertThat(faker.matz().quote(), not(isEmptyOrNullString()));
    }
}

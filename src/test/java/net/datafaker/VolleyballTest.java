package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class VolleyballTest extends AbstractFakerTest {

    @Test
    public void team() {
        assertThat(faker.volleyball().team(), not(is(emptyOrNullString())));
    }

    @Test
    public void player() {
        assertThat(faker.volleyball().player(), not(is(emptyOrNullString())));
    }

    @Test
    public void coach() {
        assertThat(faker.volleyball().coach(), not(is(emptyOrNullString())));
    }

    @Test
    public void position() {
        assertThat(faker.volleyball().position(), not(is(emptyOrNullString())));
    }

    @Test
    public void formation() {
        assertThat(faker.volleyball().formation(), not(is(emptyOrNullString())));
    }

}

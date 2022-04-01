package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class SeinfeldTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.seinfeld().character(), not(is(emptyOrNullString())));
    }

    @Test
    public void quote() {
        assertThat(faker.seinfeld().quote(), not(is(emptyOrNullString())));
    }

    @Test
    public void business() {
        assertThat(faker.seinfeld().business(), not(is(emptyOrNullString())));
    }

}

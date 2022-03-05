package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class SuperMarioTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.superMario().characters(), not(is(emptyOrNullString())));
    }

    @Test
    public void games() {
        assertThat(faker.superMario().games(), not(is(emptyOrNullString())));
    }
    @Test
    public void locations() {
        assertThat(faker.superMario().locations(), not(is(emptyOrNullString())));
    }

}

package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class BrooklynNineNineTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.brooklynNineNine().characters(), not(is(emptyOrNullString())));
    }

    @Test
    public void quotes() {
        assertThat(faker.brooklynNineNine().quotes(), not(is(emptyOrNullString())));
    }

}

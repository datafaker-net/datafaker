package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class HorseTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.horse().name(), not(is(emptyOrNullString())));
    }

    @Test
    public void breed() {
        assertThat(faker.horse().breed(), not(is(emptyOrNullString())));
    }

}

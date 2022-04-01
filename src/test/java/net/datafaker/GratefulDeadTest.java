package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class GratefulDeadTest extends AbstractFakerTest {

    @Test
    public void players() {
        assertThat(faker.gratefulDead().players(), not(is(emptyOrNullString())));
    }

    @Test
    public void songs() {
        assertThat(faker.gratefulDead().songs(), not(is(emptyOrNullString())));
    }

}

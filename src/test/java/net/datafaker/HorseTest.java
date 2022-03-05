package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class HorseTest extends AbstractFakerTest {

    @Test
    public void horse() {
        assertThat(faker.horse().horse(), not(is(emptyOrNullString())));
    }

}

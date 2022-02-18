package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

public class MountainTest extends AbstractFakerTest {

    @Test
    public void testMountainName() {
        String mountainName = faker.mountain().name();
        assertThat(mountainName, not(is(emptyOrNullString())));
    }

    @Test
    public void testMountainLeague() {
        String mountainLeague = faker.mountain().range();
        assertThat(mountainLeague, not(is(emptyOrNullString())));
    }
}

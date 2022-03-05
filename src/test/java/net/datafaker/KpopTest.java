
package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class KpopTest extends AbstractFakerTest {

    @Test
    public void iGroups() {
        assertThat(faker.kpop().iGroups(), not(is(emptyOrNullString())));
    }

    @Test
    public void iiGroups() {
        assertThat(faker.kpop().iiGroups(), not(is(emptyOrNullString())));
    }

    @Test
    public void iiiGroups() {
        assertThat(faker.kpop().iiiGroups(), not(is(emptyOrNullString())));
    }

    @Test
    public void girlGroups() {
        assertThat(faker.kpop().girlGroups(), not(is(emptyOrNullString())));
    }

    @Test
    public void boyBands() {
        assertThat(faker.kpop().boyBands(), not(is(emptyOrNullString())));
    }

    @Test
    public void solo() {
        assertThat(faker.kpop().solo(), not(is(emptyOrNullString())));
    }

}

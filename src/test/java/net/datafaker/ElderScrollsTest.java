

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ElderScrollsTest extends AbstractFakerTest {

    @Test
    public void testCity() {
        assertThat(faker.elderScrolls().city(), not(is(emptyOrNullString())));
    }

    @Test
    public void testCreature() {
        assertThat(faker.elderScrolls().creature(), not(is(emptyOrNullString())));
    }

    @Test
    public void testDragon() {
        assertThat(faker.elderScrolls().dragon(), not(is(emptyOrNullString())));
    }

    @Test
    public void testFirstName() {
        assertThat(faker.elderScrolls().firstName(), not(is(emptyOrNullString())));
    }

    @Test
    public void testLastName() {
        assertThat(faker.elderScrolls().lastName(), not(is(emptyOrNullString())));
    }

    @Test
    public void testRace() {
        assertThat(faker.elderScrolls().race(), not(is(emptyOrNullString())));
    }

    @Test
    public void testRegion() {
        assertThat(faker.elderScrolls().region(), not(is(emptyOrNullString())));
    }

    @Test
    public void testQuote() {
        assertThat(faker.elderScrolls().quote(), not(is(emptyOrNullString())));
    }
}
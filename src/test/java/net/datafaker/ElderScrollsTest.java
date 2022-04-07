

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElderScrollsTest extends AbstractFakerTest {

    @Test
    public void testCity() {
        assertThat(faker.elderScrolls().city()).isNotEmpty();
    }

    @Test
    public void testCreature() {
        assertThat(faker.elderScrolls().creature()).isNotEmpty();
    }

    @Test
    public void testDragon() {
        assertThat(faker.elderScrolls().dragon()).isNotEmpty();
    }

    @Test
    public void testFirstName() {
        assertThat(faker.elderScrolls().firstName()).isNotEmpty();
    }

    @Test
    public void testLastName() {
        assertThat(faker.elderScrolls().lastName()).isNotEmpty();
    }

    @Test
    public void testRace() {
        assertThat(faker.elderScrolls().race()).isNotEmpty();
    }

    @Test
    public void testRegion() {
        assertThat(faker.elderScrolls().region()).isNotEmpty();
    }

    @Test
    public void testQuote() {
        assertThat(faker.elderScrolls().quote()).isNotEmpty();
    }
}

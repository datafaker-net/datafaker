

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElderScrollsTest extends AbstractFakerTest {

    @Test
    void testCity() {
        assertThat(faker.elderScrolls().city()).isNotEmpty();
    }

    @Test
    void testCreature() {
        assertThat(faker.elderScrolls().creature()).isNotEmpty();
    }

    @Test
    void testDragon() {
        assertThat(faker.elderScrolls().dragon()).isNotEmpty();
    }

    @Test
    void testFirstName() {
        assertThat(faker.elderScrolls().firstName()).isNotEmpty();
    }

    @Test
    void testLastName() {
        assertThat(faker.elderScrolls().lastName()).isNotEmpty();
    }

    @Test
    void testRace() {
        assertThat(faker.elderScrolls().race()).isNotEmpty();
    }

    @Test
    void testRegion() {
        assertThat(faker.elderScrolls().region()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.elderScrolls().quote()).isNotEmpty();
    }
}

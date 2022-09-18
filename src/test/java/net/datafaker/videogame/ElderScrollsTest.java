

package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ElderScrollsTest extends VideoGameFakerTest {

    @Test
    void testCity() {
        Assertions.assertThat(faker.elderScrolls().city()).isNotEmpty();
    }

    @Test
    void testCreature() {
        Assertions.assertThat(faker.elderScrolls().creature()).isNotEmpty();
    }

    @Test
    void testDragon() {
        Assertions.assertThat(faker.elderScrolls().dragon()).isNotEmpty();
    }

    @Test
    void testFirstName() {
        Assertions.assertThat(faker.elderScrolls().firstName()).isNotEmpty();
    }

    @Test
    void testLastName() {
        Assertions.assertThat(faker.elderScrolls().lastName()).isNotEmpty();
    }

    @Test
    void testRace() {
        Assertions.assertThat(faker.elderScrolls().race()).isNotEmpty();
    }

    @Test
    void testRegion() {
        Assertions.assertThat(faker.elderScrolls().region()).isNotEmpty();
    }

    @Test
    void testQuote() {
        Assertions.assertThat(faker.elderScrolls().quote()).isNotEmpty();
    }
}

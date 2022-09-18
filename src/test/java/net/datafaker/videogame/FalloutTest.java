package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class FalloutTest extends VideoGameFakerTest {

    @Test
    void testCharacter() {
        Assertions.assertThat(faker.fallout().character()).isNotEmpty();
    }

    @Test
    void testFaction() {
        Assertions.assertThat(faker.fallout().faction()).isNotEmpty();
    }

    @Test
    void testLocation() {
        Assertions.assertThat(faker.fallout().location()).isNotEmpty();
    }

    @Test
    void testQuote() {
        Assertions.assertThat(faker.fallout().quote()).isNotEmpty();
    }
}

package ner.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FalloutTest extends VideoGameFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.fallout().character()).isNotEmpty();
    }

    @Test
    void testFaction() {
        assertThat(faker.fallout().faction()).isNotEmpty();
    }

    @Test
    void testLocation() {
        assertThat(faker.fallout().location()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.fallout().quote()).isNotEmpty();
    }
}

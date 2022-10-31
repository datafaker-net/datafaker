package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ControlTest extends VideoGameFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.control().character()).isNotEmpty();
    }

    @Test
    void testLocation() {
        assertThat(faker.control().location()).isNotEmpty();
    }

    @Test
    void testObjectOfPower() {
        assertThat(faker.control().objectOfPower()).isNotEmpty();
    }

    @Test
    void testAlteredItem() {
        assertThat(faker.control().alteredItem()).isNotEmpty();
    }

    @Test
    void testAlteredWorldEvent() {
        assertThat(faker.control().alteredWorldEvent()).isNotEmpty();
    }

    @Test
    void testHiss() {
        assertThat(faker.control().hiss()).isNotEmpty();
    }

    @Test
    void testTheBoard() {
        assertThat(faker.control().theBoard()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.control().quote()).isNotEmpty();
    }

}

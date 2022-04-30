package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameOfThronesTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.gameOfThrones().character()).matches("[A-Za-z'\\-() ]+");
    }

    @Test
    void house() {
        assertThat(faker.gameOfThrones().house()).matches("[A-Za-z' ]+");
    }

    @Test
    void city() {
        assertThat(faker.gameOfThrones().city()).matches("[A-Za-z' ]+");
    }

    @Test
    void dragon() {
        assertThat(faker.gameOfThrones().dragon()).matches("\\w+");
    }

    @Test
    void quote() {
        assertThat(faker.gameOfThrones().quote()).isNotEmpty();
    }
}

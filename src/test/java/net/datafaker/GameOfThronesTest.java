package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOfThronesTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.gameOfThrones().character()).matches("[A-Za-z'\\-() ]+");
    }

    @Test
    public void house() {
        assertThat(faker.gameOfThrones().house()).matches("[A-Za-z' ]+");
    }

    @Test
    public void city() {
        assertThat(faker.gameOfThrones().city()).matches("[A-Za-z' ]+");
    }

    @Test
    public void dragon() {
        assertThat(faker.gameOfThrones().dragon()).matches("\\w+");
    }

    @Test
    public void quote() {
        assertThat(faker.gameOfThrones().quote()).isNotEmpty();
    }
}

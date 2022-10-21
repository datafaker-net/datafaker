package net.datafaker.providers.movie;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonTest extends MovieFakerTest {

    @Test
    void name() {
        assertThat(faker.pokemon().name()).matches("[\\w']+.?( \\w+)?");
    }

    @Test
    void location() {
        assertThat(faker.pokemon().location()).matches("\\w+( \\w+)?( \\w+)?");
    }

    @RepeatedTest(100)
    void move() {
        assertThat(faker.pokemon().move()).matches("[ \\-\\w+]+");
    }

    @RepeatedTest(100)
    void move2() {
        assertThat(faker.pokemon().move()).matches("\\w+(\\s|-)?(\\w+)?(\\s|-)?(\\w+)?");
    }

}

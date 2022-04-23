package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PokemonTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.pokemon().name()).matches("[\\w']+.?( \\w+)?");
    }

    @Test
    public void location() {
        assertThat(faker.pokemon().location()).matches("\\w+( \\w+)?( \\w+)?");
    }
}

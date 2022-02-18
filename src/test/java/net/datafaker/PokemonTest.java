package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class PokemonTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.pokemon().name(), matchesRegularExpression("[\\w']+.?( \\w+)?"));
    }

    @Test
    public void location() {
        assertThat(faker.pokemon().location(), matchesRegularExpression("\\w+( \\w+)?( \\w+)?"));
    }
}

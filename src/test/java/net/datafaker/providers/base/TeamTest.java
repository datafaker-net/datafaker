package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testName() {
        assertThat(faker.team().name()).matches("(\\w+( )?){2,4}");
    }

    @Test
    void testCreature() {
        assertThat(faker.team().creature()).matches("\\w+( \\w+)?");
    }

    @Test
    void testState() {
        assertThat(faker.team().state()).matches("(\\w+( )?){1,2}");
    }

    @Test
    void testStateWithZaLocale() {
        BaseFaker zaFaker = new BaseFaker(new Locale("en", "ZA"));
        assertThat(zaFaker.team().state()).isNotEmpty();
    }

    @Test
    void testSport() {
        assertThat(faker.team().sport()).matches("(\\p{L}|\\s)+");
    }
}

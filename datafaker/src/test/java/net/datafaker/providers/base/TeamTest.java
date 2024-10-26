package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest extends BaseFakerTest<BaseFaker> {

    private final Team team = faker.team();

    @Test
    void testName() {
        assertThat(team.name()).matches("(\\w+( )?){2,4}");
    }

    @Test
    void testState() {
        assertThat(faker.team().state()).matches("(\\w+( )?){1,2}");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(team::creature, "team.creature"),
            TestSpec.of(team::sport, "team.sport", "(?:\\p{L}|\\s)+"));
    }

    @Test
    void testStateWithZaLocale() {
        BaseFaker zaFaker = new BaseFaker(new Locale("en", "ZA"));
        assertThat(zaFaker.team().state()).isNotEmpty();
    }
}

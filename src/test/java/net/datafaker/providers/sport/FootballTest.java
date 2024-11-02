package net.datafaker.providers.sport;

import net.datafaker.providers.base.BaseFakerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

class FootballTest extends SportFakerTest {

    private final Football football = getFaker().football();

    private static Collection<TestSpec> getProviderListTests(Football football) {
        return List.of(
            TestSpec.of(football::coaches, "football.coaches"),
            TestSpec.of(football::competitions, "football.competitions"),
            TestSpec.of(football::players, "football.players"),
            TestSpec.of(football::positions, "football.positions"),
            TestSpec.of(football::teams, "football.teams")
        );
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return getProviderListTests(football);
    }

    @Nested
    class FootballInGreekTest extends BaseFakerTest<SportFaker> {

        @BeforeAll
        void setup() {
            this.setFaker(new SportFaker(new Locale("el", "GR")));
        }

        @AfterAll
        void reset() {
            this.setFaker(new SportFaker());
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            Football football = faker.football();
            return getProviderListTests(football);
        }
    }
}

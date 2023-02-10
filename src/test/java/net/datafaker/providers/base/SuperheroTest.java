package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class SuperheroTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testName() {
        assertThat(faker.superhero().name()).matches("[A-Za-z' -/]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        Superhero superhero = faker.superhero();
        return Arrays.asList(TestSpec.of(superhero::prefix, "superhero.prefix"),
            TestSpec.of(superhero::suffix, "superhero.suffix"),
            TestSpec.of(superhero::power, "superhero.power"),
            TestSpec.of(superhero::descriptor, "superhero.descriptor"));
    }
}

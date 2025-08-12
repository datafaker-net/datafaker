package net.datafaker.providers.base;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class GreekPhilosopherTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void testName() {
        assertThat(faker.greekPhilosopher().name()).matches("^[a-zA-Z ]+$");
    }

    @RepeatedTest(10)
    void testQuote() {
        assertThat(faker.greekPhilosopher().quote()).matches("^[a-zA-Z ,.']+$");
    }

    private static Collection<TestSpec> getProviderListTests(GreekPhilosopher greekPhilosopher) {
        return List.of(
            TestSpec.of(greekPhilosopher::name, "greek_philosophers.names"),
            TestSpec.of(greekPhilosopher::quote, "greek_philosophers.quotes")
        );
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        GreekPhilosopher greekPhilosopher = faker.greekPhilosopher();
        return getProviderListTests(greekPhilosopher);
    }

    @Nested
    class GreekPhilosopherInGreekTest extends BaseFakerTest<BaseFaker> {

        @Override
        protected final BaseFaker getFaker() {
            return new BaseFaker(new Locale("el", "GR"));
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            GreekPhilosopher greekPhilosopher = faker.greekPhilosopher();
            return getProviderListTests(greekPhilosopher);
        }
    }
}

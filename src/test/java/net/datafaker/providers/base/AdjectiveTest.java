package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AdjectiveTest extends BaseFakerTest {

    private final Adjective adjective = faker.adjective();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(adjective::positive, "adjective.positive"),
            TestSpec.of(adjective::negative, "adjective.negative")
        );
    }

    @RepeatedTest(10)
    void any() {
        assertThat(adjective.any()).isNotNull().isNotEmpty();
    }

    @RepeatedTest(10)
    void positive() {
        assertThat(adjective.positive()).isNotNull().isNotEmpty();
    }

    @RepeatedTest(10)
    void negative() {
        assertThat(adjective.negative()).isNotNull().isNotEmpty();
    }
}
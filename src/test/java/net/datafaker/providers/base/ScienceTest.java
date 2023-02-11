package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class ScienceTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.science().unit(), "science.unit"));
    }
    @RepeatedTest(10)
    void element() {
        assertThat(faker.science().element()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void elementSymbol() {
        assertThat(faker.science().elementSymbol()).matches("[A-Za-z]{1,2}");
    }

    @RepeatedTest(10)
    void scientist() {
        assertThat(faker.science().scientist()).matches("[A-Za-z. -]+");
    }

    @RepeatedTest(10)
    void tool() {
        assertThat(faker.science().tool()).matches("[0-9A-Za-z. -]+");
    }

    @RepeatedTest(10)
    void quark() {
        assertThat(faker.science().quark()).matches("[A-Za-z]+");
    }

    @RepeatedTest(10)
    void leptons() {
        assertThat(faker.science().leptons()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void bosons() {
        assertThat(faker.science().bosons()).matches("[A-Za-z ]+");
    }
}

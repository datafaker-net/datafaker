package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class ScienceTest extends BaseFakerTest<BaseFaker> {

    Science science = faker.science();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(science::unit, "science.unit"));
    }

    @RepeatedTest(10)
    void element() {
        assertThat(science.element()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void elementSymbol() {
        assertThat(science.elementSymbol()).matches("[A-Za-z]{1,2}");
    }

    @RepeatedTest(10)
    void scientist() {
        assertThat(science.scientist()).matches("[A-Za-z. -]+");
    }

    @RepeatedTest(10)
    void tool() {
        assertThat(science.tool()).matches("[0-9A-Za-z. -]+");
    }

    @RepeatedTest(10)
    void quark() {
        assertThat(science.quark()).matches("[A-Za-z]+");
    }

    @RepeatedTest(10)
    void leptons() {
        assertThat(science.leptons()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void bosons() {
        assertThat(science.bosons()).matches("[A-Za-z ]+");
    }
}

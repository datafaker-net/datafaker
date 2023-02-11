package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ChiquitoTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Chiquito chiquito = faker.chiquito();
        return Arrays.asList(TestSpec.of(chiquito::expressions, "chiquito.expressions"),
            TestSpec.of(chiquito::terms, "chiquito.terms"),
            TestSpec.of(chiquito::sentences, "chiquito.sentences"),
            TestSpec.of(chiquito::jokes, "chiquito.jokes"));
    }

}

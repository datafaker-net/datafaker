package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ChiquitoTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Chiquito chiquito = faker.chiquito();
        return List.of(TestSpec.of(chiquito::expressions, "chiquito.expressions"),
            TestSpec.of(chiquito::terms, "chiquito.terms"),
            TestSpec.of(chiquito::sentences, "chiquito.sentences"),
            TestSpec.of(chiquito::jokes, "chiquito.jokes"));
    }

}

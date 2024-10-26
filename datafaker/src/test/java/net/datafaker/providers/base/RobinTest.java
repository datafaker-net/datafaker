package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class RobinTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Robin robin = faker.robin();
        return List.of(TestSpec.of(robin::quote, "robin.quotes", "^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$"));
    }
}

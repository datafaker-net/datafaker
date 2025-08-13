package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class RobinTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Robin robin = faker.robin();
        return List.of(TestSpec.of(robin::quote, "robin.quotes", "^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$"));
    }
}

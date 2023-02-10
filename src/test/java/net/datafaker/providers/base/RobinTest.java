package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class RobinTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Robin robin = faker.robin();
        return Arrays.asList(TestSpec.of(robin::quote, "robin.quotes", "^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$"));
    }
}

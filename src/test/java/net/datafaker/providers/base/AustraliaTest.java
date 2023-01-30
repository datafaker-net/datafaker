package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class AustraliaTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.australia().locations(), "australia.locations"),
            TestSpec.of(() -> faker.australia().animals(), "australia.animals"),
            TestSpec.of(() -> faker.australia().states(), "australia.states"));
    }

}

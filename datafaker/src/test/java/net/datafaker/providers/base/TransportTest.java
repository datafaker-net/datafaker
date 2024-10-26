package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

public class TransportTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Transport transport = faker.transport();
        return List.of(TestSpec.of(transport::type, "transport.type"));
    }

}

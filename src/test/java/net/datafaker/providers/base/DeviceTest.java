package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class DeviceTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.device().modelName(), "device.model_name"),
                TestSpec.of(() -> faker.device().platform(), "device.platform"),
                TestSpec.of(() -> faker.device().manufacturer(), "device.manufacturer"),
                TestSpec.of(() -> faker.device().serial(), "device.serial"));
    }

}

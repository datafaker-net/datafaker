package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class DeviceTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.device().modelName(), "device.model_name"),
                TestSpec.of(() -> faker.device().platform(), "device.platform"),
                TestSpec.of(() -> faker.device().manufacturer(), "device.manufacturer"),
                TestSpec.of(() -> faker.device().serial(), "device.serial"));
    }

}

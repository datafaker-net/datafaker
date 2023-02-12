package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class DeviceTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Device device = faker.device();
        return List.of(TestSpec.of(device::modelName, "device.model_name"),
                TestSpec.of(device::platform, "device.platform"),
                TestSpec.of(device::manufacturer, "device.manufacturer"),
                TestSpec.of(device::serial, "device.serial"));
    }

}

package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CameraTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Camera camera = faker.camera();
        return List.of(TestSpec.of(camera::brand, "camera.brand", "^[a-zA-Z0-9 -]+$"),
            TestSpec.of(camera::model, "camera.model", "^[a-zA-Z0-9 -]+$"),
            TestSpec.of(camera::brandWithModel, "camera.brand_with_model", "^[a-zA-Z0-9 -]+$"));
    }
}

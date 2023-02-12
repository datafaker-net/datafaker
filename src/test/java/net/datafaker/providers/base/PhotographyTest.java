package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class PhotographyTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Photography photo = faker.photography();
        return List.of(TestSpec.of(photo::aperture, "photography.aperture"),
            TestSpec.of(photo::term, "photography.term"),
            TestSpec.of(photo::brand, "photography.brand"),
            TestSpec.of(photo::camera, "photography.camera"),
            TestSpec.of(photo::lens, "photography.lens"),
            TestSpec.of(photo::genre, "photography.genre"),
            TestSpec.of(photo::imageTag, "photography.imagetag"),
            TestSpec.of(photo::shutter, "photography.shutter", "\\d+/?\\d*"),
            TestSpec.of(photo::iso, "photography.iso", "\\d+"));
    }
}

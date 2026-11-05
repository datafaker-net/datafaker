package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class FileListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        File file = faker.file();
        return List.of(
            TestSpec.of(file::extension, "file.extension"),
            TestSpec.of(file::mimeType, "file.mime_type"));
    }
}

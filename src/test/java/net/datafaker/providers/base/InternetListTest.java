package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class InternetListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Internet internet = faker.internet();
        return List.of(
            TestSpec.of(internet::domainSuffix, "internet.domain_suffix"),
            TestSpec.of(internet::httpMethod, "internet.http_method"),
            TestSpec.of(() -> faker.resolve("internet.free_email"), "internet.free_email"),
            TestSpec.of(() -> faker.resolve("internet.safe_email"), "internet.safe_email"),
            TestSpec.of(() -> faker.resolve("internet.image_dimension"), "internet.image_dimension"));
    }
}

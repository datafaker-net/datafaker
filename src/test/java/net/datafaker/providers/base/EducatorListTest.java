package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class EducatorListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(() -> faker.resolve("educator.name"), "educator.name"),
            TestSpec.of(() -> faker.resolve("educator.secondary"), "educator.secondary"),
            TestSpec.of(() -> faker.resolve("educator.tertiary.type"), "educator.tertiary.type"),
            TestSpec.of(() -> faker.resolve("educator.tertiary.degree.type"), "educator.tertiary.degree.type"),
            TestSpec.of(() -> faker.resolve("educator.tertiary.degree.subject"), "educator.tertiary.degree.subject"));
    }
}

package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class NameListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Name name = faker.name();
        return List.of(
            TestSpec.of(name::femaleFirstName, "name.female_first_name"),
            TestSpec.of(name::maleFirstName, "name.male_first_name"),
            TestSpec.of(name::lastName, "name.last_name"),
            TestSpec.of(name::prefix, "name.prefix"),
            TestSpec.of(name::suffix, "name.suffix"),
            TestSpec.of(() -> faker.resolve("name.title.descriptor"), "name.title.descriptor"),
            TestSpec.of(() -> faker.resolve("name.title.level"), "name.title.level"),
            TestSpec.of(() -> faker.resolve("name.title.job"), "name.title.job"));
    }
}

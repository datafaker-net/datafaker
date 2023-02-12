package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AppTest extends BaseFakerTest<BaseFaker> {

    private final App app = faker.app();

    @Test
    void testVersion() {
        assertThat(app.version()).matches("\\d\\.(?:\\d){1,2}(?:\\.\\d)?");
    }

    @Test
    void testAuthor() {
        assertThat(app.author()).matches("(?:[\\w']+[-&,.]? ?){2,9}");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(app::name, "app.name", "([\\w-]+ ?)+"));
    }
}

package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class UniversityTest extends BaseFakerTest<BaseFaker> {

    private University university = faker.university();

    @Test
    void testName() {
        assertThat(university.name()).matches("[A-Za-z'() ]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(university::prefix, "university.prefix"),
            TestSpec.of(university::suffix, "university.suffix"));
    }
}

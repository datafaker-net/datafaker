package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class UniversityTest extends BaseFakerTest<BaseFaker> {

    private static final String UNIVERSITY_MATCHER = "[A-Za-z'() öèü-]+";
    private final University university = faker.university();

    @Test
    void testName() {
        assertThat(university.name()).matches(UNIVERSITY_MATCHER);
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(university::prefix, "university.prefix"),
            TestSpec.of(university::suffix, "university.suffix"),
            TestSpec.of(university::degree, "university.degree"),
            TestSpec.of(university::place, "university.place", UNIVERSITY_MATCHER));
    }
}

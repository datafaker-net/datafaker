package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testName() {
        assertThat(faker.university().name()).matches("[A-Za-z'() ]+");
    }

    @Test
    void testPrefix() {
        assertThat(faker.university().prefix()).matches("\\w+");
    }

    @Test
    void testSuffix() {
        assertThat(faker.university().suffix()).matches("\\w+");
    }
}

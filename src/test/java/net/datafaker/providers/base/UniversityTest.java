package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.university().name()).matches("[A-Za-z'() ]+");
    }

    @Test
    void prefix() {
        assertThat(faker.university().prefix()).matches("\\w+");
    }

    @Test
    void suffix() {
        assertThat(faker.university().suffix()).matches("\\w+");
    }
}

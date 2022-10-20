package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndustrySegmentsTest extends BaseFakerTest<BaseFaker> {

    @Test
    void industry() {
        assertThat(faker.industrySegments().industry()).isNotEmpty();
    }

    @Test
    void superSector() {
        assertThat(faker.industrySegments().superSector()).isNotEmpty();
    }

    @Test
    void sector() {
        assertThat(faker.industrySegments().sector()).isNotEmpty();
    }

    @Test
    void subSector() {
        assertThat(faker.industrySegments().subSector()).isNotEmpty();
    }
}

package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndustrySegmentsTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testIndustry() {
        assertThat(faker.industrySegments().industry()).isNotEmpty();
    }

    @Test
    void testSuperSector() {
        assertThat(faker.industrySegments().superSector()).isNotEmpty();
    }

    @Test
    void testSector() {
        assertThat(faker.industrySegments().sector()).isNotEmpty();
    }

    @Test
    void testSubSector() {
        assertThat(faker.industrySegments().subSector()).isNotEmpty();
    }
}

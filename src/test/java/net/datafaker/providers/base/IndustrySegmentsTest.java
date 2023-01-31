package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

public class IndustrySegmentsTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.industrySegments().industry(), "industry_segments.industry"),
                TestSpec.of(() -> faker.industrySegments().superSector(), "industry_segments.super_sector"),
                TestSpec.of(() -> faker.industrySegments().sector(), "industry_segments.sector"),
                TestSpec.of(() -> faker.industrySegments().subSector(), "industry_segments.sub_sector"));
    }
}

package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

public class IndustrySegmentsTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        IndustrySegments industrySegment = faker.industrySegments();
        return List.of(TestSpec.of(industrySegment::industry, "industry_segments.industry"),
                TestSpec.of(industrySegment::superSector, "industry_segments.super_sector"),
                TestSpec.of(industrySegment::sector, "industry_segments.sector"),
                TestSpec.of(industrySegment::subSector, "industry_segments.sub_sector"));
    }
}

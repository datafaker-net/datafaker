package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class IndustrySegments extends AbstractProvider<BaseProviders> {

    protected IndustrySegments(BaseProviders faker) {
        super(faker);
    }

    public String industry() {
        return resolve("industry_segments.industry");
    }

    public String superSector() {
        return resolve("industry_segments.super_sector");
    }

    public String sector() {
        return resolve("industry_segments.sector");
    }

    public String subSector() {
        return resolve("industry_segments.sub_sector");
    }

}

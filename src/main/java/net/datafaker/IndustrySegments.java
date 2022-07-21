package net.datafaker;

public class IndustrySegments {

    private final Faker faker;

    protected IndustrySegments(Faker faker) {
        this.faker = faker;
    }

    public String industry() {
        return faker.resolve("industry_segments.industry");
    }

    public String superSector() {
        return faker.resolve("industry_segments.super_sector");
    }

    public String sector() {
        return faker.resolve("industry_segments.sector");
    }

    public String subSector() {
        return faker.resolve("industry_segments.sub_sector");
    }

}

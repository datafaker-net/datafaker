package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 1.5.0
 */
class Cannabis {
    private final Faker faker;

    protected Cannabis(Faker faker) {
        this.faker = faker;
    }

    public String strains() {
        return faker.fakeValuesService().resolve("cannabis.strains", this, faker);
    }

    public String cannabinoidAbbreviations() {
        return faker.fakeValuesService().resolve("cannabis.cannabinoid_abbreviations", this, faker);
    }

    public String cannabinoids() {
        return faker.fakeValuesService().resolve("cannabis.cannabinoids", this, faker);
    }

    public String terpenes() {
        return faker.fakeValuesService().resolve("cannabis.terpenes", this, faker);
    }

    public String medicalUses() {
        return faker.fakeValuesService().resolve("cannabis.medical_uses", this, faker);
    }

    public String healthBenefits() {
        return faker.fakeValuesService().resolve("cannabis.health_benefits", this, faker);
    }

    public String categories() {
        return faker.fakeValuesService().resolve("cannabis.categories", this, faker);
    }

    public String types() {
        return faker.fakeValuesService().resolve("cannabis.types", this, faker);
    }

    public String buzzwords() {
        return faker.fakeValuesService().resolve("cannabis.buzzwords", this, faker);
    }

    public String brands() {
        return faker.fakeValuesService().resolve("cannabis.brands", this, faker);
    }
}

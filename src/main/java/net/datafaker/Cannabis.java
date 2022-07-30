package net.datafaker;

/**
 * @since 1.5.0
 */
public class Cannabis extends AbstractProvider {

    protected Cannabis(Faker faker) {
        super(faker);
    }

    public String strains() {
        return faker.fakeValuesService().resolve("cannabis.strains", this);
    }

    public String cannabinoidAbbreviations() {
        return faker.fakeValuesService().resolve("cannabis.cannabinoid_abbreviations", this);
    }

    public String cannabinoids() {
        return faker.fakeValuesService().resolve("cannabis.cannabinoids", this);
    }

    public String terpenes() {
        return faker.fakeValuesService().resolve("cannabis.terpenes", this);
    }

    public String medicalUses() {
        return faker.fakeValuesService().resolve("cannabis.medical_uses", this);
    }

    public String healthBenefits() {
        return faker.fakeValuesService().resolve("cannabis.health_benefits", this);
    }

    public String categories() {
        return faker.fakeValuesService().resolve("cannabis.categories", this);
    }

    public String types() {
        return faker.fakeValuesService().resolve("cannabis.types", this);
    }

    public String buzzwords() {
        return faker.fakeValuesService().resolve("cannabis.buzzwords", this);
    }

    public String brands() {
        return faker.fakeValuesService().resolve("cannabis.brands", this);
    }
}

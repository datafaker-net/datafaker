package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class Cannabis extends AbstractProvider<BaseProviders> {

    protected Cannabis(BaseProviders faker) {
        super(faker);
    }

    public String strains() {
        return resolve("cannabis.strains");
    }

    public String cannabinoidAbbreviations() {
        return resolve("cannabis.cannabinoid_abbreviations");
    }

    public String cannabinoids() {
        return resolve("cannabis.cannabinoids");
    }

    public String terpenes() {
        return resolve("cannabis.terpenes");
    }

    public String medicalUses() {
        return resolve("cannabis.medical_uses");
    }

    public String healthBenefits() {
        return resolve("cannabis.health_benefits");
    }

    public String categories() {
        return resolve("cannabis.categories");
    }

    public String types() {
        return resolve("cannabis.types");
    }

    public String buzzwords() {
        return resolve("cannabis.buzzwords");
    }

    public String brands() {
        return resolve("cannabis.brands");
    }
}

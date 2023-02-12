package net.datafaker.providers.base;

/**
 * Generate random, different kinds of disease.
 *
 * @since 0.8.0
 */
public class Disease extends AbstractProvider<BaseProviders> {

    /**
     * Create a constructor for Disease
     *
     * @param faker The Faker instance for generating random, different kinds of disease, e.g. the internal disease.
     */
    protected Disease(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generate random internal disease
     *
     * @return An internal disease
     */
    public String internalDisease() {
        return resolve("disease.internal_disease");
    }

    /**
     * Generate random neurology disease
     *
     * @return A neurology disease
     */
    public String neurology() {
        return resolve("disease.neurology");
    }

    /**
     * Generate random surgery disease
     *
     * @return A surgery disease
     */
    public String surgery() {
        return resolve("disease.surgery");
    }

    /**
     * Generate random paediattics disease
     *
     * @return A paediatrics disease
     */
    public String paediatrics() {
        return resolve("disease.paediatrics");
    }

    /**
     * Generate random gynecology and obstetrics disease
     *
     * @return A gynecology and obstetrics disease
     */
    public String gynecologyAndObstetrics() {
        return resolve("disease.gynecology_and_obstetrics");
    }

    /**
     * Generate random ophthalmology and otorhinolaryngology disease
     *
     * @return A ophthalmology and otorhinolaryngology disease
     */
    public String ophthalmologyAndOtorhinolaryngology() {
        return resolve("disease.ophthalmology_and_otorhinolaryngology");
    }

    /**
     * Generate random dermatology disease
     *
     * @return A dermatology disease
     * @since 1.8.0
     */
    public String dermatology() {
        return resolve("disease.dermatology");
    }
}

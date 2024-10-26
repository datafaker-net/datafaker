package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

import static net.datafaker.providers.healthcare.Disease.DiseaseType.DERMATOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.INTERNAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.NEUROLOGICAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.PAEDIATRIC_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.SURGICAL_DISEASE_KEY;

/**
 * Generate random, different kinds of disease.
 *
 * @since 0.8.0
 */
public class Disease extends AbstractProvider<HealthcareProviders> {

    public enum DiseaseType {
        INTERNAL_DISEASE_KEY("healthcare.disease.internal_disease"),
        NEUROLOGICAL_DISEASE_KEY( "healthcare.disease.neurology"),
        SURGICAL_DISEASE_KEY ("healthcare.disease.surgery"),
        PAEDIATRIC_DISEASE_KEY("healthcare.disease.paediatrics"),
        GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY("healthcare.disease.gynecology_and_obstetrics"),
        OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY("healthcare.disease.ophthalmology_and_otorhinolaryngology"),
        DERMATOLOGY_DISEASE_KEY("healthcare.disease.dermatology");

        final String yamlKey;

        DiseaseType(String yamlKey) {
            this.yamlKey = yamlKey;
        }
    }

    /**
     * Create a constructor for Disease
     *
     * @param faker The Faker instance for generating random, different kinds of disease, e.g. the internal disease.
     */
    protected Disease(HealthcareProviders faker) {
        super(faker);
    }

    public String icd10() {
        return faker.regexify(resolve("healthcare.disease.icd10"));
    }

    public String anyDisease() {
        return resolve(faker.options().option(DiseaseType.class));
    }

    /**
     * Generate random internal disease
     *
     * @return An internal disease
     */
    public String internalDisease() {
        return resolve(INTERNAL_DISEASE_KEY);
    }

    /**
     * Generate random neurology disease
     *
     * @return A neurology disease
     */
    public String neurology() {
        return resolve(NEUROLOGICAL_DISEASE_KEY);
    }

    /**
     * Generate random surgery disease
     *
     * @return A surgery disease
     */
    public String surgery() {
        return resolve(SURGICAL_DISEASE_KEY);
    }

    /**
     * Generate random paediatrics disease
     *
     * @return A paediatrics disease
     */
    public String paediatrics() {
        return resolve(PAEDIATRIC_DISEASE_KEY);
    }

    /**
     * Generate random gynecology and obstetrics disease
     *
     * @return A gynecology and obstetrics disease
     */
    public String gynecologyAndObstetrics() {
        return resolve(GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY);
    }

    /**
     * Generate random ophthalmology and otorhinolaryngology disease
     *
     * @return A ophthalmology and otorhinolaryngology disease
     */
    public String ophthalmologyAndOtorhinolaryngology() {
        return resolve(OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY);
    }

    /**
     * Generate random dermatology disease
     *
     * @return A dermatology disease
     * @since 1.8.0
     */
    public String dermatology() {
        return resolve(DERMATOLOGY_DISEASE_KEY);
    }

    private String resolve(DiseaseType diseaseType) {
        return resolve(diseaseType.yamlKey);
    }
}

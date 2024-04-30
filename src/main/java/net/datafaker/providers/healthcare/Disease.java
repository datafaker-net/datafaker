package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

import java.util.List;
import java.util.function.Supplier;

/**
 * Generate random, different kinds of disease.
 *
 * @since 0.8.0
 */
public class Disease extends AbstractProvider<HealthcareProviders> {

    static final String INTERNAL_DISEASE_KEY = "healthcare.disease.internal_disease";
    static final String NEUROLOGICAL_DISEASE_KEY = "healthcare.disease.neurology";
    static final String SURGICAL_DISEASE_KEY = "healthcare.disease.surgery";
    static final String PAEDIATRIC_DISEASE_KEY = "healthcare.disease.paediatrics";
    static final String GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY = "healthcare.disease.gynecology_and_obstetrics";
    static final String OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY = "healthcare.disease.ophthalmology_and_otorhinolaryngology";
    static final String DERMATOLOGY_DISEASE_KEY = "healthcare.disease.dermatology";
    
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
        List<Supplier<String>> providers = List.of(
            this::internalDisease,
            this::neurology,
            this::surgery,
            this::paediatrics,
            this::gynecologyAndObstetrics,
            this::ophthalmologyAndOtorhinolaryngology,
            this::dermatology
        );
        Supplier<String> selectedProvider = providers.get(faker.random().nextInt(providers.size()));
        return selectedProvider.get();
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
     * Generate random paediattics disease
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
}

package net.datafaker.providers.base;

/**
 * @since 0.8.0
 * @deprecated since 2.3.0. This faker is deprecated due to migration
 * to healthcare-specific aggregated into {@link net.datafaker.providers.healthcare.HealthcareFaker} fakers with similar methods.
 */
@Deprecated(since = "2.3.0", forRemoval = true)
public class Medical extends AbstractProvider<BaseProviders> {

    protected Medical(BaseProviders faker) {
        super(faker);
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.Medication#drugName()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String medicineName() {
        return resolve("medical.medicine_name");
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.Disease#anyDisease()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String diseaseName() {
        return resolve("medical.disease_name");
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.CareProvider#hospitalName()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String hospitalName() {
        return resolve("medical.hospital_name");
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.Observation#symptom()} instead
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String symptoms() {
        return resolve("medical.symptoms");
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.Disease#icd10()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String diagnosisCode() {
        String regex = resolve("medical.diagnosis_code.icd10");
        return faker.regexify(regex);
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.MedicalProcedure#icd10()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String procedureCode() {
        String regex = resolve("medical.procedure_code.icd10");
        return faker.regexify(regex);
    }

    /**
     * @deprecated since 2.3.0. Use {@link net.datafaker.providers.healthcare.CareProvider#medicalProfession()} instead.
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public String medicalProfession() {
        return resolve("medical.medical_professions");
    }

}

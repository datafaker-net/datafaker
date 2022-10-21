package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Medical extends AbstractProvider<BaseProviders> {

    protected Medical(BaseProviders faker) {
        super(faker);
    }

    public String medicineName() {
        return resolve("medical.medicine_name");
    }

    public String diseaseName() {
        return resolve("medical.disease_name");
    }

    public String hospitalName() {
        return resolve("medical.hospital_name");
    }

    public String symptoms() {
        return resolve("medical.symptoms");
    }

    public String diagnosisCode() {
        String regex = resolve("medical.diagnosis_code.icd10");
        return faker.regexify(regex);
    }

    public String procedureCode() {
        String regex = resolve("medical.procedure_code.icd10");
        return faker.regexify(regex);
    }
}

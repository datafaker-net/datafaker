package net.datafaker;

/**
 * @since 0.8.0
 */
public class Medical extends AbstractProvider {

    protected Medical(Faker faker) {
        super(faker);
    }

    public String medicineName() {
        return faker.fakeValuesService().resolve("medical.medicine_name", this);
    }

    public String diseaseName() {
        return faker.fakeValuesService().resolve("medical.disease_name", this);
    }

    public String hospitalName() {
        return faker.fakeValuesService().resolve("medical.hospital_name", this);
    }

    public String symptoms() {
        return faker.fakeValuesService().resolve("medical.symptoms", this);
    }

    public String diagnosisCode() {
        String regex = faker.fakeValuesService().resolve("medical.diagnosis_code.icd10", this);
        return faker.regexify(regex);
    }

    public String procedureCode() {
        String regex = faker.fakeValuesService().resolve("medical.procedure_code.icd10", this);
        return faker.regexify(regex);
    }
}

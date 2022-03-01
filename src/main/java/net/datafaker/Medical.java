package net.datafaker;

/**
 * @since 0.8.0
 */
public class Medical {

    private final Faker faker;

    protected Medical(Faker faker) {
        this.faker = faker;
    }

    public String medicineName() {
        return faker.fakeValuesService().resolve("medical.medicine_name", this, faker);
    }

    public String diseaseName() {
        return faker.fakeValuesService().resolve("medical.disease_name", this, faker);
    }

    public String hospitalName() {
        return faker.fakeValuesService().resolve("medical.hospital_name", this, faker);
    }

    public String symptoms() {
        return faker.fakeValuesService().resolve("medical.symptoms", this, faker);
    }

    public String diagnosisCode() {
        String regex = faker.fakeValuesService().resolve("medical.diagnosis_code.icd10", this, faker);
        return faker.regexify(regex);
    }

    public String procedureCode() {
        String regex = faker.fakeValuesService().resolve("medical.procedure_code.icd10", this, faker);
        return faker.regexify(regex);
    }
}

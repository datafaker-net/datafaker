package net.datafaker;

/**
 * @since 1.4.0
 */
public class BloodType {
    private final Faker faker;

    protected BloodType(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method returns a ABO blood type
     *
     * @return a string of ABO blood type
     */
    public String abo_types() {
        return faker.fakeValuesService().fetchString("blood_type.abo_types");
    }

    /**
     * This method returns an Rh blood type
     *
     * @return a string of Rh blood type
     */
    public String rh_types() {
        return faker.fakeValuesService().fetchString("blood_type.rh_types");
    }

    /**
     * This method returns a P blood type
     *
     * @return a string of P blood type
     */
    public String p_types() {
        return faker.fakeValuesService().fetchString("blood_type.p_types");
    }
}

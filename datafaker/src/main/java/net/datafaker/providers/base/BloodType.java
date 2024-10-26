package net.datafaker.providers.base;

/**
 * @since 1.4.0
 */
public class BloodType extends AbstractProvider<BaseProviders> {

    protected BloodType(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method returns a ABO blood type
     *
     * @return a string of ABO blood type
     */
    public String aboTypes() {
        return resolve("blood_type.abo_types");
    }

    /**
     * This method returns an Rh blood type
     *
     * @return a string of Rh blood type
     */
    public String rhTypes() {
        return resolve("blood_type.rh_types");
    }

    /**
     * This method returns a P blood type
     *
     * @return a string of P blood type
     */
    public String pTypes() {
        return resolve("blood_type.p_types");
    }


    /**
     * Returns a blood group such as O−, O+, A-, A+, B-, B+, AB-, AB+
     *
     * @return a blood group such as O−, O+, A-, A+, B-, B+, AB-, AB+
     */
    public String bloodGroup() {
        return resolve("blood_type.blood_group");
    }
}

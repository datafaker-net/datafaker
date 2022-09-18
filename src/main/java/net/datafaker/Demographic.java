package net.datafaker;

/**
 * @since 0.8.0
 */
public class Demographic extends AbstractProvider<IProviders> {

    protected Demographic(BaseFaker faker) {
        super(faker);
    }

    public String race() {
        return resolve("demographic.race");
    }

    public String educationalAttainment() {
        return resolve("demographic.educational_attainment");
    }

    public String demonym() {
        return resolve("demographic.demonym");
    }

    public String sex() {
        return resolve("demographic.sex");
    }

    public String maritalStatus() {
        return resolve("demographic.marital_status");
    }
}

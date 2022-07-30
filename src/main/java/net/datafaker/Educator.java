package net.datafaker;

/**
 * @since 0.8.0
 */
public class Educator extends AbstractProvider {

    protected Educator(Faker faker) {
        super(faker);
    }

    // TODO - move these all out to en.yml by default. 
    public String university() {
        return faker.fakeValuesService().resolve("educator.name", this)
            + " "
            + faker.fakeValuesService().resolve("educator.tertiary.type", this);
    }

    public String course() {
        return faker.fakeValuesService().resolve("educator.tertiary.degree.type", this)
            + " "
            + faker.fakeValuesService().resolve("educator.tertiary.degree.subject", this);
    }

    public String secondarySchool() {
        return faker.fakeValuesService().resolve("educator.name", this)
            + " "
            + faker.fakeValuesService().resolve("educator.secondary", this);
    }

    public String campus() {
        return faker.fakeValuesService().resolve("educator.name", this) + " Campus";
    }

}

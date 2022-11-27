package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Educator extends AbstractProvider<BaseProviders> {

    protected Educator(BaseProviders faker) {
        super(faker);
    }

    // TODO - move these all out to en.yml by default. 
    public String university() {
        return resolve("educator.name")
            + " "
            + resolve("educator.tertiary.type");
    }

    public String course() {
        return resolve("educator.tertiary.degree.type")
            + " "
            + resolve("educator.tertiary.degree.subject");
    }

    public String subjectWithNumber() {
        return resolve("educator.tertiary.degree.subject")
            + " "
            + faker.numerify(resolve("educator.tertiary.degree.course_number"));
    }

    public String secondarySchool() {
        return resolve("educator.name")
            + " "
            + resolve("educator.secondary");
    }

    public String campus() {
        return resolve("educator.name") + " Campus";
    }
}

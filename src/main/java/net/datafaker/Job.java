package net.datafaker;

/**
 * @since 0.8.0
 */
public class Job extends AbstractProvider {

    public Job(final Faker faker) {
        super(faker);
    }

    public String field() {
        return faker.fakeValuesService().resolve("job.field", this);
    }

    public String seniority() {
        return faker.fakeValuesService().resolve("job.seniority", this);
    }

    public String position() {
        return faker.fakeValuesService().resolve("job.position", this);
    }

    public String keySkills() {
        return faker.fakeValuesService().resolve("job.key_skills", this);
    }

    public String title() {
        return faker.fakeValuesService().resolve("job.title", this);
    }
}

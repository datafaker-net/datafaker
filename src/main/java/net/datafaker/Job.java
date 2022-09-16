package net.datafaker;

/**
 * @since 0.8.0
 */
public class Job extends AbstractProvider {

    public Job(final Faker faker) {
        super(faker);
    }

    public String field() {
        return resolve("job.field");
    }

    public String seniority() {
        return resolve("job.seniority");
    }

    public String position() {
        return resolve("job.position");
    }

    public String keySkills() {
        return resolve("job.key_skills");
    }

    public String title() {
        return resolve("job.title");
    }
}

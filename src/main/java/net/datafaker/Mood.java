package net.datafaker;

/**
 * @since 0.9.0
 */
public class Mood extends AbstractProvider {

    protected Mood(Faker faker) {
        super(faker);
    }

    public String feeling() {
        return faker.fakeValuesService().resolve("mood.feeling", this);
    }

    public String emotion() {
        return faker.fakeValuesService().resolve("mood.emotion", this);
    }

    public String tone() {
        return faker.fakeValuesService().resolve("mood.tone", this);
    }

}

package net.datafaker;

/**
 * @since 0.9.0
 */
public class Mood extends AbstractProvider {

    protected Mood(Faker faker) {
        super(faker);
    }

    public String feeling() {
        return resolve("mood.feeling");
    }

    public String emotion() {
        return resolve("mood.emotion");
    }

    public String tone() {
        return resolve("mood.tone");
    }

}

package net.datafaker.providers.base;

/**
 * @since 0.9.0
 */
public class Mood extends AbstractProvider<BaseProviders> {

    protected Mood(BaseProviders faker) {
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

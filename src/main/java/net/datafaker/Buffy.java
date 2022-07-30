package net.datafaker;

/**
 * @since 0.8.0
 */
public class Buffy extends AbstractProvider {

    protected Buffy(Faker faker) {
        super(faker);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("buffy.characters", this);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("buffy.quotes", this);
    }

    public String celebrities() {
        return faker.fakeValuesService().resolve("buffy.celebrities", this);
    }

    public String bigBads() {
        return faker.fakeValuesService().resolve("buffy.big_bads", this);
    }

    public String episodes() {
        return faker.fakeValuesService().resolve("buffy.episodes", this);
    }

}

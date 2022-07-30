package net.datafaker;

/**
 * @since 0.8.0
 */
public class ElderScrolls extends AbstractProvider {

    protected ElderScrolls(Faker faker) {
        super(faker);
    }

    public String race() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.race", this);
    }

    public String creature() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.creature", this);
    }

    public String region() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.region", this);
    }

    public String dragon() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.dragon", this);
    }

    public String city() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.city", this);
    }

    public String firstName() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.first_name", this);
    }

    public String lastName() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.last_name", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.elder_scrolls.quote", this);
    }
}

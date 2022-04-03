package net.datafaker;

/**
 * Mountaineering, or alpinism, is the set of outdoor activities that involves ascending tall mountains.
 *
 * @since 1.4.0
 */
public class Mountaineering {

    private final Faker faker;

    protected Mountaineering(Faker faker) {
        this.faker = faker;
    }

    public String mountaineer() {
        return faker.fakeValuesService().resolve("mountaineering.mountaineer", this, faker);
    }

}

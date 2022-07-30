package net.datafaker;

/**
 * Mountaineering, or alpinism, is the set of outdoor activities that involves ascending tall mountains.
 *
 * @since 1.4.0
 */
public class Mountaineering extends AbstractProvider {

    protected Mountaineering(Faker faker) {
        super(faker);
    }

    public String mountaineer() {
        return faker.fakeValuesService().resolve("mountaineering.mountaineer", this);
    }

}

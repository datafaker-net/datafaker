package net.datafaker;

/**
 * @since 0.8.0
 */
public class Bool extends AbstractProvider {

    protected Bool(Faker faker) {
        super(faker);
    }

    public boolean bool() {
        return faker.random().nextBoolean();
    }
}

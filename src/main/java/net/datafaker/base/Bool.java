package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Bool extends AbstractProvider<BaseProviders> {

    protected Bool(BaseFaker faker) {
        super(faker);
    }

    public boolean bool() {
        return faker.random().nextBoolean();
    }
}

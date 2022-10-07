package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Cat extends AbstractProvider<BaseProviders> {

    protected Cat(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("creature.cat.name");
    }

    public String breed() {
        return resolve("creature.cat.breed");
    }

    public String registry() {
        return resolve("creature.cat.registry");
    }
}

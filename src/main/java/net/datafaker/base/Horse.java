package net.datafaker.base;

/**
 * @since 1.3.0
 */
public class Horse extends AbstractProvider<IProviders> {

    protected Horse(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("creature.horse.name");
    }

    public String breed() {
        return resolve("creature.horse.breed");
    }

}

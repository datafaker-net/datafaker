package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class LordOfTheRings extends AbstractProvider<IProviders> {

    protected LordOfTheRings(final BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("lord_of_the_rings.characters");
    }

    public String location() {
        return resolve("lord_of_the_rings.locations");
    }
}

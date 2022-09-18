package net.datafaker.base;

/**
 * @since 1.5.0
 */
public class Simpsons extends AbstractProvider<IProviders> {

    public Simpsons(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("simpsons.characters");
    }

    public String location() {
        return resolve("simpsons.locations");
    }

    public String quote() {
        return resolve("simpsons.quotes");
    }
}

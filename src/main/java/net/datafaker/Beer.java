package net.datafaker;

/**
 * @since 0.8.0
 */
public class Beer extends AbstractProvider {

    protected Beer(Faker faker) {
        super(faker);
    }

    public String name() {
        return resolve("beer.name");
    }

    public String style() {
        return resolve("beer.style");
    }

    public String hop() {
        return resolve("beer.hop");
    }

    public String yeast() {
        return resolve("beer.yeast");
    }

    public String malt() {
        return resolve("beer.malt");
    }
}

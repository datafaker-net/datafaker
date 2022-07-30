package net.datafaker;

/**
 * @since 0.8.0
 */
public class Beer extends AbstractProvider {

    protected Beer(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("beer.name", this);
    }

    public String style() {
        return faker.fakeValuesService().resolve("beer.style", this);
    }

    public String hop() {
        return faker.fakeValuesService().resolve("beer.hop", this);
    }

    public String yeast() {
        return faker.fakeValuesService().resolve("beer.yeast", this);
    }

    public String malt() {
        return faker.fakeValuesService().resolve("beer.malt", this);
    }
}

package net.datafaker;

/**
 * @since 0.8.0
 */
public class StarTrek extends AbstractProvider {

    protected StarTrek(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("star_trek.character", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("star_trek.location", this);
    }

    public String species() {
        return faker.fakeValuesService().resolve("star_trek.species", this);
    }

    public String villain() {
        return faker.fakeValuesService().resolve("star_trek.villain", this);
    }

    public String klingon() {
        return faker.fakeValuesService().resolve("star_trek.klingon", this);
    }
}

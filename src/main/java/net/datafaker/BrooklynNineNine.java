package net.datafaker;

/**
 * Brooklyn Nine-Nine is an American police procedural comedy television series.
 *
 * @since 1.3.0
 */
public class BrooklynNineNine {

    private final Faker faker;

    protected BrooklynNineNine(Faker faker) {
        this.faker = faker;
    }

    public String characters() {
        return faker.fakeValuesService().resolve("brooklyn_nine_nine.characters", this, faker);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("brooklyn_nine_nine.quotes", this, faker);
    }

}

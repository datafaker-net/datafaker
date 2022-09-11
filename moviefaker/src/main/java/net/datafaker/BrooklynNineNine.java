package net.datafaker;

/**
 * Brooklyn Nine-Nine is an American police procedural comedy television series.
 *
 * @since 1.3.0
 */
public class BrooklynNineNine extends MovieProvider {

    protected BrooklynNineNine(MovieFaker faker) {
        super(faker);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("brooklyn_nine_nine.characters", this);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("brooklyn_nine_nine.quotes", this);
    }

}

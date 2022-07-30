package net.datafaker;

/**
 * @since 0.8.0
 */
public class HowIMetYourMother extends AbstractProvider {

    protected HowIMetYourMother(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("how_i_met_your_mother.character", this);
    }

    public String catchPhrase() {
        return faker.fakeValuesService().resolve("how_i_met_your_mother.catch_phrase", this);
    }

    public String highFive() {
        return faker.fakeValuesService().resolve("how_i_met_your_mother.high_five", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("how_i_met_your_mother.quote", this);
    }
}

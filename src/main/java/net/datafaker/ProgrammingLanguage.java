package net.datafaker;

/**
 * @since 0.8.0
 */
public class ProgrammingLanguage extends AbstractProvider {

    public ProgrammingLanguage(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("programming_language.name", this);
    }

    public String creator() {
        return faker.fakeValuesService().resolve("programming_language.creator", this);
    }

}

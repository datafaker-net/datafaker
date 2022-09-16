package net.datafaker;

/**
 * @since 0.8.0
 */
public class ProgrammingLanguage extends AbstractProvider {

    public ProgrammingLanguage(Faker faker) {
        super(faker);
    }

    public String name() {
        return resolve("programming_language.name");
    }

    public String creator() {
        return resolve("programming_language.creator");
    }

}

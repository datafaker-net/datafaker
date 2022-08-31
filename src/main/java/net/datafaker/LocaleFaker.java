package net.datafaker;

public class LocaleFaker extends AbstractProvider {

    protected LocaleFaker(Faker faker) {
        super(faker);
    }

    /**
     *
     * @return locale in form: "en-US"
     */
    public String baseLocale() {
        return faker.resolve("locale.base_locale_id");
    }

    /**
     *
     * @return locale in form: "English (United States)"
     */
    public String displayName() {
        return faker.resolve("locale.display_name");
    }
}

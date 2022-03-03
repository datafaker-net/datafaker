package net.datafaker;

import java.nio.file.Paths;
import java.util.Locale;

/**
 * @since 1.2.0
 */
public class Nigeria {
    private static final String KEY = "nigeria";
    private final Faker faker;

    protected Nigeria(Faker faker) {
        this.faker = faker;
        faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/main/resources/en/nigeria.yml"));
    }

    public String places() {
        return faker.fakeValuesService().resolve(KEY + ".places", this, faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve(KEY + ".name", this, faker);
    }

    public String food() {
        return faker.fakeValuesService().resolve(KEY + ".food", this, faker);
    }

    public String schools() {
        return faker.fakeValuesService().resolve(KEY + ".schools", this, faker);
    }

    public String celebrities() {
        return faker.fakeValuesService().resolve(KEY + ".celebrities", this, faker);
    }
}




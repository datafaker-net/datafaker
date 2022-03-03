package net.datafaker;

import java.nio.file.Paths;
import java.util.Locale;

public class Nigeria {
    private static final String KEY = "nigeria";
    private final Faker faker;

    protected Nigeria(Faker faker) {
        this.faker = faker;
        faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("target/classes/en/nigeria.yml"));
    }

    public String places() {
        return faker.fakeValuesService().resolve("nigeria.places", this, faker);
    }

    public String name() {
        return  faker.fakeValuesService().resolve("nigeria.name", this, faker);
    }

    public String food() {
        return faker.fakeValuesService().resolve("nigeria.food", this, faker);
    }

    public String schools() {
        return faker.fakeValuesService().resolve("nigeria.schools", this, faker);
    }

    public String celebrities(){
        return faker.fakeValuesService().resolve("nigeria.celebrities", this, faker);
    }
}




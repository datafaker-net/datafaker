package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class SiliconValley extends AbstractProvider<EntertainmentProviders> {

    protected SiliconValley(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("silicon_valley.characters");
    }

    public String company() {
        return faker.resolve("silicon_valley.companies");
    }

    public String quote() {
        return faker.resolve("silicon_valley.quotes");
    }

    public String app() {
        return faker.resolve("silicon_valley.apps");
    }

    public String invention() {
        return faker.resolve("silicon_valley.inventions");
    }

    public String motto() {
        return faker.resolve("silicon_valley.mottos");
    }

    public String url() {
        return faker.resolve("silicon_valley.urls");
    }

    public String email() {
        return faker.resolve("silicon_valley.email");
    }

}

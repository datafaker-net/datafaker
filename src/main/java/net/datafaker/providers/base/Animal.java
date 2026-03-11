package net.datafaker.providers.base;

import static net.datafaker.internal.helper.WordUtils.capitalizeWords;

/**
 * @since 0.8.0
 */
public class Animal extends AbstractProvider<BaseProviders> {

    protected Animal(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("creature.animal.name");
    }

    public String scientificName() {
        return genus() + " " + species();
    }

    public String genus() {
        return capitalizeWords(faker.resolve("creature.animal.genus"));
    }

    public String species() {
        return resolve("creature.animal.species").toLowerCase();
    }
}

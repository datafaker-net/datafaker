package net.datafaker.providers.food;


import net.datafaker.providers.base.AbstractProvider;

import java.util.Locale;

/**
 * @since 1.5.0
 */
public class Coffee extends AbstractProvider<FoodProviders> {

    protected Coffee(FoodProviders faker) {
        super(faker);
    }

    public String country() {
        return resolve("coffee.country");
    }

    public String region() {
        return region(faker.options().option(Coffee.Country.class));
    }

    public String region(Coffee.Country country) {
        return resolve("coffee.regions." + country.name().toLowerCase(Locale.ROOT));
    }

    public String variety() {
        return resolve("coffee.variety");
    }

    public String intensifier() {
        return resolve("coffee.intensifier");
    }

    public String body() {
        return resolve("coffee.body");
    }

    public String descriptor() {
        return resolve("coffee.descriptor");
    }

    public String notes() {
        return resolve("coffee.notes");
    }

    public String name1() {
        return resolve("coffee.name_1");
    }

    public String name2() {
        return resolve("coffee.name_2");
    }

    public String blendName() {
        return resolve("coffee.blend_name");
    }

    public enum Country {
        BRAZIL,
        COLOMBIA,
        SUMATRA,
        ETHIOPIA,
        HONDURAS,
        KENYA,
        UGANDA,
        MEXICO,
        GUATEMALA,
        NICARAGUA,
        COSTA_RICA,
        TANZANIA,
        EL_SALVADOR,
        RWANDA,
        BURUNDI,
        PANAMA,
        YEMEN,
        INDIA
    }
}

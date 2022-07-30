package net.datafaker;


import java.util.Locale;

/**
 * @since 1.5.0
 */
public class Coffee extends AbstractProvider {

    protected Coffee(Faker faker) {
        super(faker);
    }

    public String country() {
        return faker.fakeValuesService().resolve("coffee.country", this);
    }

    public String region() {
        return region(faker.options().option(Coffee.Country.class));
    }

    public String region(Coffee.Country country) {
        return faker.fakeValuesService().resolve("coffee.regions." + country.name().toLowerCase(Locale.ROOT), this);
    }

    public String variety() {
        return faker.fakeValuesService().resolve("coffee.variety", this);
    }

    public String intensifier() {
        return faker.fakeValuesService().resolve("coffee.intensifier", this);
    }

    public String body() {
        return faker.fakeValuesService().resolve("coffee.body", this);
    }

    public String descriptor() {
        return faker.fakeValuesService().resolve("coffee.descriptor", this);
    }

    public String notes() {
        return faker.fakeValuesService().resolve("coffee.notes", this);
    }

    public String name1() {
        return faker.fakeValuesService().resolve("coffee.name_1", this);
    }

    public String name2() {
        return faker.fakeValuesService().resolve("coffee.name_2", this);
    }

    public String blendName() {
        return faker.fakeValuesService().resolve("coffee.blend_name", this);
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

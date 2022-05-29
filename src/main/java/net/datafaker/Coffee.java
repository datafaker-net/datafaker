package net.datafaker;


import java.util.Locale;

/**
 * @since 1.5.0
 */
class Coffee {

    private final Faker faker;

    protected Coffee(Faker faker) {
        this.faker = faker;
    }

    public String country() {
        return faker.fakeValuesService().resolve("coffee.country", this, faker);
    }

    public String region() {
        return region(faker.options().option(Coffee.Country.class));
    }

    public String region(Coffee.Country country) {
        return faker.fakeValuesService().resolve("coffee.regions." + country.name().toLowerCase(Locale.ROOT), this, faker);
    }

    public String variety() {
        return faker.fakeValuesService().resolve("coffee.variety", this, faker);
    }

    public String intensifier() {
        return faker.fakeValuesService().resolve("coffee.intensifier", this, faker);
    }

    public String body() {
        return faker.fakeValuesService().resolve("coffee.body", this, faker);
    }

    public String descriptor() {
        return faker.fakeValuesService().resolve("coffee.descriptor", this, faker);
    }

    public String notes() {
        return faker.fakeValuesService().resolve("coffee.notes", this, faker);
    }

    public String name1() {
        return faker.fakeValuesService().resolve("coffee.name_1", this, faker);
    }

    public String name2() {
        return faker.fakeValuesService().resolve("coffee.name_2", this, faker);
    }

    public String blendName() {
        return faker.fakeValuesService().resolve("coffee.blend_name", this, faker);
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

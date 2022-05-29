package net.datafaker;


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

    public String regions() {
        return faker.fakeValuesService().resolve("coffee.regions", this, faker);
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

}

package net.datafaker;

/**
 * @since 1.5.0
 */
class Construction {

    private final Faker faker;

    protected Construction(Faker faker) {
        this.faker = faker;
    }

    public String heavyEquipment() {
        return faker.fakeValuesService().resolve("construction.heavy_equipment", this, faker);
    }

    public String materials() {
        return faker.fakeValuesService().resolve("construction.materials", this, faker);
    }

    public String subcontractCategories() {
        return faker.fakeValuesService().resolve("construction.subcontract_categories", this, faker);
    }

    public String roles() {
        return faker.fakeValuesService().resolve("construction.roles", this, faker);
    }

    public String trades() {
        return faker.fakeValuesService().resolve("construction.trades", this, faker);
    }

    public String standardCostCodes() {
        return faker.fakeValuesService().resolve("construction.standard_cost_codes", this, faker);
    }

}

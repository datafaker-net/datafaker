package net.datafaker;

/**
 * @since 1.5.0
 */
public class Construction extends AbstractProvider {

    protected Construction(Faker faker) {
        super(faker);
    }

    public String heavyEquipment() {
        return faker.fakeValuesService().resolve("construction.heavy_equipment", this);
    }

    public String materials() {
        return faker.fakeValuesService().resolve("construction.materials", this);
    }

    public String subcontractCategories() {
        return faker.fakeValuesService().resolve("construction.subcontract_categories", this);
    }

    public String roles() {
        return faker.fakeValuesService().resolve("construction.roles", this);
    }

    public String trades() {
        return faker.fakeValuesService().resolve("construction.trades", this);
    }

    public String standardCostCodes() {
        return faker.fakeValuesService().resolve("construction.standard_cost_codes", this);
    }

}

package net.datafaker;

/**
 * @since 1.5.0
 */
public class Construction extends AbstractProvider {

    protected Construction(Faker faker) {
        super(faker);
    }

    public String heavyEquipment() {
        return resolve("construction.heavy_equipment");
    }

    public String materials() {
        return resolve("construction.materials");
    }

    public String subcontractCategories() {
        return resolve("construction.subcontract_categories");
    }

    public String roles() {
        return resolve("construction.roles");
    }

    public String trades() {
        return resolve("construction.trades");
    }

    public String standardCostCodes() {
        return resolve("construction.standard_cost_codes");
    }

}

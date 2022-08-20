package net.datafaker;

/**
 * @since 1.6.0
 */
public class ClashOfClans extends AbstractProvider {

    protected ClashOfClans(Faker faker) {
        super(faker);
    }

    public String troop() {
        return faker.fakeValuesService().resolve("clash_of_clans.troops", this);
    }

    public String rank() {
        return faker.fakeValuesService().resolve("clash_of_clans.ranks", this);
    }

    public String defensiveBuilding() {
        return faker.fakeValuesService().resolve("clash_of_clans.defensive_buildings", this);
    }
}

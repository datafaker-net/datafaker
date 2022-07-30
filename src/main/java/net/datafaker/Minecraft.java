package net.datafaker;

/**
 * @since 0.9.0
 */
public class Minecraft extends AbstractProvider {

    public Minecraft(Faker faker) {
        super(faker);
    }

    public String itemName() {
        return faker.fakeValuesService().resolve("minecraft.item_name", this);
    }

    public String tileName() {
        return faker.fakeValuesService().resolve("minecraft.tile_name", this);
    }

    public String entityName() {
        return faker.fakeValuesService().resolve("minecraft.entity_name", this);
    }

    public String monsterName() {
        return faker.fakeValuesService().resolve("minecraft.monster_name", this);
    }

    public String animalName() {
        return faker.fakeValuesService().resolve("minecraft.animal_name", this);
    }

    public String tileItemName() {
        return faker.random().nextBoolean() ? itemName() : tileName();
    }
}

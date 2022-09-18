package net.datafaker.base;

/**
 * @since 0.9.0
 */
public class Minecraft extends AbstractProvider<IProviders> {

    public Minecraft(BaseFaker faker) {
        super(faker);
    }

    public String itemName() {
        return resolve("minecraft.item_name");
    }

    public String tileName() {
        return resolve("minecraft.tile_name");
    }

    public String entityName() {
        return resolve("minecraft.entity_name");
    }

    public String monsterName() {
        return resolve("minecraft.monster_name");
    }

    public String animalName() {
        return resolve("minecraft.animal_name");
    }

    public String tileItemName() {
        return faker.random().nextBoolean() ? itemName() : tileName();
    }
}

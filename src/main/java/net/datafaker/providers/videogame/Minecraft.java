package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Minecraft is a sandbox game developed by Mojang Studios.
 *
 * @since 0.9.0
 */
public class Minecraft extends AbstractProvider<VideoGameProviders> {

    public Minecraft(VideoGameProviders faker) {
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

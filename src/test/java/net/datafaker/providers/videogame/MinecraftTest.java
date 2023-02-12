package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


class MinecraftTest extends VideoGameFakerTest {

    @Test
    void testTileItemName() {
        assertThat(faker.minecraft().tileItemName()).matches("([\\w()']+\\.?( )?){2,5}");
    }

    private final Minecraft minecraft = getFaker().minecraft();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(minecraft::animalName, "minecraft.animal_name"),
            TestSpec.of(minecraft::entityName, "minecraft.entity_name"),
            TestSpec.of(minecraft::itemName, "minecraft.item_name"),
            TestSpec.of(minecraft::monsterName, "minecraft.monster_name"),
            TestSpec.of(minecraft::tileName, "minecraft.tile_name")
        );
    }
}

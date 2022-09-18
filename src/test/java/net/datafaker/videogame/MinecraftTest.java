package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class MinecraftTest extends VideoGameFakerTest {

    @Test
    void testItemName() {
        Assertions.assertThat(faker.minecraft().itemName()).matches("([\\w'()]+\\.?( )?){2,4}");
    }

    @Test
    void testTileName() {
        Assertions.assertThat(faker.minecraft().tileName()).matches("([\\w'()]+\\.?( )?){2,5}");
    }

    @Test
    void testEntityName() {
        Assertions.assertThat(faker.minecraft().entityName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testMonsterName() {
        Assertions.assertThat(faker.minecraft().monsterName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testAnimalName() {
        Assertions.assertThat(faker.minecraft().animalName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testTileItemName() {
        Assertions.assertThat(faker.minecraft().tileItemName()).matches("([\\w()']+\\.?( )?){2,5}");
    }

}

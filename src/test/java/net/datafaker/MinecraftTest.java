package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinecraftTest extends AbstractFakerTest {

    @Test
    void testItemName() {
        assertThat(faker.minecraft().itemName()).matches("([\\w'()]+\\.?( )?){2,4}");
    }

    @Test
    void testTileName() {
        assertThat(faker.minecraft().tileName()).matches("([\\w'()]+\\.?( )?){2,5}");
    }

    @Test
    void testEntityName() {
        assertThat(faker.minecraft().entityName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testMonsterName() {
        assertThat(faker.minecraft().monsterName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testAnimalName() {
        assertThat(faker.minecraft().animalName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testTileItemName() {
        assertThat(faker.minecraft().tileItemName()).matches("([\\w()']+\\.?( )?){2,5}");
    }

}

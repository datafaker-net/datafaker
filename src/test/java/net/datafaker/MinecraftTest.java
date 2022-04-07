package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinecraftTest extends AbstractFakerTest {

    @Test
    public void testItemName() {
        assertThat(faker.minecraft().itemName()).matches("([\\w'()]+\\.?( )?){2,4}");
    }

    @Test
    public void testTileName() {
        assertThat(faker.minecraft().tileName()).matches("([\\w'()]+\\.?( )?){2,5}");
    }

    @Test
    public void testEntityName() {
        assertThat(faker.minecraft().entityName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    public void testMonsterName() {
        assertThat(faker.minecraft().monsterName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    public void testAnimalName() {
        assertThat(faker.minecraft().animalName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    public void testTileItemName() {
        assertThat(faker.minecraft().tileItemName()).matches("([\\w()']+\\.?( )?){2,5}");
    }

}

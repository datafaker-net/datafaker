package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MinecraftTest extends VideoGameFakerTest {

    @Test
    void itemName() {
        assertThat(faker.minecraft().itemName()).matches("([\\w'()]+\\.?( )?){2,4}");
    }

    @Test
    void tileName() {
        assertThat(faker.minecraft().tileName()).matches("([\\w'()]+\\.?( )?){2,5}");
    }

    @Test
    void entityName() {
        assertThat(faker.minecraft().entityName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void monsterName() {
        assertThat(faker.minecraft().monsterName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void animalName() {
        assertThat(faker.minecraft().animalName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void tileItemName() {
        assertThat(faker.minecraft().tileItemName()).matches("([\\w()']+\\.?( )?){2,5}");
    }

}

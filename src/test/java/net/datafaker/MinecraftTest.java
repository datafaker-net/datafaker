package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class MinecraftTest extends AbstractFakerTest {

    @Test
    public void testItemName() {
        assertThat(faker.minecraft().itemName(), matchesRegularExpression("([\\w'()]+\\.?( )?){2,4}"));
    }

    @Test
    public void testTileName() {
        assertThat(faker.minecraft().tileName(), matchesRegularExpression("([\\w'()]+\\.?( )?){2,5}"));
    }

    @Test
    public void testEntityName() {
        assertThat(faker.minecraft().entityName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testMonsterName() {
        assertThat(faker.minecraft().monsterName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testAnimalName() {
        assertThat(faker.minecraft().animalName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testTileItemName() {
        assertThat(faker.minecraft().tileItemName(), matchesRegularExpression("([\\w()']+\\.?( )?){2,5}"));
    }

}

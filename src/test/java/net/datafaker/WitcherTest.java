package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WitcherTest extends AbstractFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.witcher().character()).matches("[A-Za-z' -éúï]+");
    }

    @Test
    void testWitcher() {
        assertThat(faker.witcher().witcher()).matches("[A-Za-z -ëúï]+");
    }

    @Test
    void testSchool() {
        assertThat(faker.witcher().school()).matches("[A-Za-z]+");
    }

    @Test
    void testLocation() {
        assertThat(faker.witcher().location()).matches("[A-Za-z -áâé]+");
    }

    @Test
    void testQuote() {
        assertThat(faker.witcher().quote()).matches("[-A-Za-z0-9 —;…?!.’‘'”“,\\[\\]]+");
    }

    @Test
    void testMonster() {
        assertThat(faker.witcher().monster()).matches("[A-Za-z -]+");
    }

    @Test
    void testSign() {
        assertThat(faker.witcher().sign()).matches("[A-Za-z -]+");
    }

    @Test
    void testPotion() {
        assertThat(faker.witcher().potion()).matches("[A-Za-z '-]+");
    }

    @Test
    void testBook() {
        assertThat(faker.witcher().book()).matches("[A-Za-z -]+");
    }
}

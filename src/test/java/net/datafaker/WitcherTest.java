package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WitcherTest extends AbstractFakerTest {

    @Test
    public void testCharacter() {
        assertThat(faker.witcher().character()).matches("[A-Za-z' -éúï]+");
    }

    @Test
    public void testWitcher() {
        assertThat(faker.witcher().witcher()).matches("[A-Za-z -ëúï]+");
    }

    @Test
    public void testSchool() {
        assertThat(faker.witcher().school()).matches("[A-Za-z]+");
    }

    @Test
    public void testLocation() {
        assertThat(faker.witcher().location()).matches("[A-Za-z -áâé]+");
    }

    @Test
    public void testQuote() {
        assertThat(faker.witcher().quote()).matches("[-A-Za-z0-9 —;…?!.’‘'”“,\\[\\]]+");
    }

    @Test
    public void testMonster() {
        assertThat(faker.witcher().monster()).matches("[A-Za-z -]+");
    }

    @Test
    public void testSign() {
        assertThat(faker.witcher().sign()).matches("[A-Za-z -]+");
    }

    @Test
    public void testPotion() {
        assertThat(faker.witcher().potion()).matches("[A-Za-z '-]+");
    }

    @Test
    public void testBook() {
        assertThat(faker.witcher().book()).matches("[A-Za-z -]+");
    }
}

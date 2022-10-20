package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WitcherTest extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.witcher().character()).matches("[A-Za-z' -éúï]+");
    }

    @Test
    void witcher() {
        assertThat(faker.witcher().witcher()).matches("[A-Za-z -ëúï]+");
    }

    @Test
    void school() {
        assertThat(faker.witcher().school()).matches("[A-Za-z]+");
    }

    @Test
    void location() {
        assertThat(faker.witcher().location()).matches("[A-Za-z -áâé]+");
    }

    @Test
    void quote() {
        assertThat(faker.witcher().quote()).matches("[-A-Za-z0-9 —;…?!.’‘'”“,\\[\\]]+");
    }

    @Test
    void monster() {
        assertThat(faker.witcher().monster()).matches("[A-Za-z -]+");
    }

    @Test
    void sign() {
        assertThat(faker.witcher().sign()).matches("[A-Za-z -]+");
    }

    @Test
    void potion() {
        assertThat(faker.witcher().potion()).matches("[A-Za-z '-]+");
    }

    @Test
    void book() {
        assertThat(faker.witcher().book()).matches("[A-Za-z -]+");
    }
}

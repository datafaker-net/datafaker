package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CoffeeTest extends AbstractFakerTest {

    @Test
    public void country() {
        assertThat(faker.coffee().country()).isNotEmpty();
    }

    @Test
    public void region() {
        assertThat(faker.coffee().region()).isNotEmpty();
    }

    @Test
    public void regionBrazil() {
        assertThat(faker.coffee().region(Coffee.Country.BRAZIL)).isNotEmpty();
    }

    @Test
    public void variety() {
        assertThat(faker.coffee().variety()).isNotEmpty();
    }

    @Test
    public void intensifier() {
        assertThat(faker.coffee().intensifier()).isNotEmpty();
    }

    @Test
    public void body() {
        assertThat(faker.coffee().body()).isNotEmpty();
    }

    @Test
    public void descriptor() {
        assertThat(faker.coffee().descriptor()).isNotEmpty();
    }

    @Test
    public void notes() {
        assertThat(faker.coffee().notes()).isNotEmpty();
    }

    @Test
    public void name1() {
        assertThat(faker.coffee().name1()).isNotEmpty();
    }

    @Test
    public void name2() {
        assertThat(faker.coffee().name2()).isNotEmpty();
    }

    @Test
    public void blendName() {
        assertThat(faker.coffee().blendName()).isNotEmpty();
    }
}

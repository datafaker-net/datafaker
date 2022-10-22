package net.datafaker.providers.foods;

import net.datafaker.providers.food.Coffee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeTest extends FoodFakerTest {

    @Test
    void country() {
        assertThat(faker.coffee().country()).isNotEmpty();
    }

    @Test
    void region() {
        assertThat(faker.coffee().region()).isNotEmpty();
    }

    @Test
    void regionBrazil() {
        assertThat(faker.coffee().region(Coffee.Country.BRAZIL)).isNotEmpty();
    }

    @Test
    void variety() {
        assertThat(faker.coffee().variety()).isNotEmpty();
    }

    @Test
    void intensifier() {
        assertThat(faker.coffee().intensifier()).isNotEmpty();
    }

    @Test
    void body() {
        assertThat(faker.coffee().body()).isNotEmpty();
    }

    @Test
    void descriptor() {
        assertThat(faker.coffee().descriptor()).isNotEmpty();
    }

    @Test
    void notes() {
        assertThat(faker.coffee().notes()).isNotEmpty();
    }

    @Test
    void name1() {
        assertThat(faker.coffee().name1()).isNotEmpty();
    }

    @Test
    void name2() {
        assertThat(faker.coffee().name2()).isNotEmpty();
    }

    @Test
    void blendName() {
        assertThat(faker.coffee().blendName()).isNotEmpty();
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTest extends AbstractFakerTest {

    @Test
    void ingredient() {
        assertThat(faker.food().ingredient()).matches("[A-Za-z- ]+");
    }

    @Test
    void spice() {
        assertThat(faker.food().spice()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    void dish() {
        assertThat(faker.food().dish()).matches("\\P{Cc}+");
    }

    @Test
    void fruit() {
        assertThat(faker.food().fruit()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    void vegetable() {
        assertThat(faker.food().vegetable()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    void sushi() {
        assertThat(faker.food().sushi()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    void measurement() {
        assertThat(faker.food().measurement()).matches("([A-Za-z1-9/ ]+){2}");
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FoodTest extends AbstractFakerTest {

    @Test
    public void ingredient() {
        assertThat(faker.food().ingredient()).matches("[A-Za-z- ]+");
    }

    @Test
    public void spice() {
        assertThat(faker.food().spice()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    public void dish() {
        assertThat(faker.food().dish()).matches("\\P{Cc}+");
    }

    @Test
    public void fruit() {
        assertThat(faker.food().fruit()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    public void vegetable() {
        assertThat(faker.food().vegetable()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    public void sushi() {
        assertThat(faker.food().sushi()).matches("[A-Za-z1-9- ]+");
    }

    @Test
    public void measurement() {
        assertThat(faker.food().measurement()).matches("([A-Za-z1-9/ ]+){2}");
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DessertTest extends AbstractFakerTest {

    @Test
    public void variety() {
        assertThat(faker.dessert().variety()).matches("[A-Za-z ]+");
    }

    @Test
    public void topping() {
        assertThat(faker.dessert().topping()).matches("[A-Za-z ]+");
    }

    @Test
    public void flavor() {
        assertThat(faker.dessert().flavor()).matches("[A-Za-z ']+");
    }
}

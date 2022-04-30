package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DessertTest extends AbstractFakerTest {

    @Test
    void variety() {
        assertThat(faker.dessert().variety()).matches("[A-Za-z ]+");
    }

    @Test
    void topping() {
        assertThat(faker.dessert().topping()).matches("[A-Za-z ]+");
    }

    @Test
    void flavor() {
        assertThat(faker.dessert().flavor()).matches("[A-Za-z ']+");
    }
}

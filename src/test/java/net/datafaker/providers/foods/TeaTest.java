package net.datafaker.providers.foods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeaTest extends FoodFakerTest {

    @Test
    void testVariety() {
        assertThat(faker.tea().variety()).matches("^(?:[A-Z]['.\\-a-z]+[\\s-])*[A-Z]['.\\-a-z]+$");
    }

    @Test
    void testType() {
        assertThat(faker.tea().type()).matches("^[A-Z][a-z]+$");
    }
}

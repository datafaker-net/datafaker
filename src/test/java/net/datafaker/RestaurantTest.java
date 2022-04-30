package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest extends AbstractFakerTest {

    @Test
    void namePrefix() {
        assertThat(faker.restaurant().namePrefix()).isNotEmpty();
    }

    @Test
    void nameSuffix() {
        assertThat(faker.restaurant().nameSuffix()).isNotEmpty();
    }

    @Test
    void name() {
        assertThat(faker.restaurant().name()).isNotEmpty();
    }

    @Test
    void type() {
        assertThat(faker.restaurant().type()).isNotEmpty();
    }

    @Test
    void description() {
        assertThat(faker.restaurant().description()).isNotEmpty();
    }

    @Test
    void review() {
        assertThat(faker.restaurant().review()).isNotEmpty();
    }

}

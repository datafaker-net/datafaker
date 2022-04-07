package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTest extends AbstractFakerTest {

    @Test
    public void namePrefix() {
        assertThat(faker.restaurant().namePrefix()).isNotEmpty();
    }

    @Test
    public void nameSuffix() {
        assertThat(faker.restaurant().nameSuffix()).isNotEmpty();
    }

    @Test
    public void name() {
        assertThat(faker.restaurant().name()).isNotEmpty();
    }

    @Test
    public void type() {
        assertThat(faker.restaurant().type()).isNotEmpty();
    }

    @Test
    public void description() {
        assertThat(faker.restaurant().description()).isNotEmpty();
    }

    @Test
    public void review() {
        assertThat(faker.restaurant().review()).isNotEmpty();
    }

}

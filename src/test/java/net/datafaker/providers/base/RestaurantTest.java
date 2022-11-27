package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(100)
    void namePrefix() {
        assertThat(faker.restaurant().namePrefix())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
    }

    @Test
    void nameSuffix() {
        assertThat(faker.restaurant().nameSuffix()).isNotEmpty();
    }

    @RepeatedTest(100)
    void name() {
        assertThat(faker.restaurant().name())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
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

package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class HobbitTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.hobbit().character()).matches("^(\\(?\\w+\\.?\\s?\\)?)+$");
    }

    @Test
    void thorinsCompany() {
        Assertions.assertThat(faker.hobbit().thorinsCompany()).matches("^(\\w+\\s?)+$");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.hobbit().quote()).isNotEmpty();
    }

    @Test
    void location() {
        Assertions.assertThat(faker.hobbit().location()).matches("^(\\w+'?-?\\s?)+$");
    }
}

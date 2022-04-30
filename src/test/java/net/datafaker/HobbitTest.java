package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HobbitTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.hobbit().character()).matches("^(\\(?\\w+\\.?\\s?\\)?)+$");
    }

    @Test
    void thorinsCompany() {
        assertThat(faker.hobbit().thorinsCompany()).matches("^(\\w+\\s?)+$");
    }

    @Test
    void quote() {
        assertThat(faker.hobbit().quote()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.hobbit().location()).matches("^(\\w+'?-?\\s?)+$");
    }
}

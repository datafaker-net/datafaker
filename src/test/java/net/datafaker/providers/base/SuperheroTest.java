package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SuperheroTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.superhero().name()).matches("[A-Za-z' -/]+");
    }

    @Test
    void prefix() {
        assertThat(faker.superhero().prefix()).matches("[A-Za-z -]+");
    }

    @Test
    void suffix() {
        assertThat(faker.superhero().suffix()).matches("[A-Za-z -]+");
    }

    @Test
    void power() {
        assertThat(faker.superhero().power()).matches("[A-Za-z/ -]+");
    }

    @Test
    void descriptor() {
        assertThat(faker.superhero().descriptor()).matches("[A-Za-z' -]+");
    }
}

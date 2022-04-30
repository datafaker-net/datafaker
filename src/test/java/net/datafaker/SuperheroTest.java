package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SuperheroTest extends AbstractFakerTest {

    @Test
    void testName() {
        assertThat(faker.superhero().name()).matches("[A-Za-z' -/]+");
    }

    @Test
    void testPrefix() {
        assertThat(faker.superhero().prefix()).matches("[A-Za-z -]+");
    }

    @Test
    void testSuffix() {
        assertThat(faker.superhero().suffix()).matches("[A-Za-z -]+");
    }

    @Test
    void testPower() {
        assertThat(faker.superhero().power()).matches("[A-Za-z/ -]+");
    }

    @Test
    void testDescriptor() {
        assertThat(faker.superhero().descriptor()).matches("[A-Za-z' -]+");
    }
}

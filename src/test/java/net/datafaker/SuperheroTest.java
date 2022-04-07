package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperheroTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.superhero().name()).matches("[A-Za-z' -/]+");
    }

    @Test
    public void testPrefix() {
        assertThat(faker.superhero().prefix()).matches("[A-Za-z -]+");
    }

    @Test
    public void testSuffix() {
        assertThat(faker.superhero().suffix()).matches("[A-Za-z -]+");
    }

    @Test
    public void testPower() {
        assertThat(faker.superhero().power()).matches("[A-Za-z/ -]+");
    }

    @Test
    public void testDescriptor() {
        assertThat(faker.superhero().descriptor()).matches("[A-Za-z' -]+");
    }
}

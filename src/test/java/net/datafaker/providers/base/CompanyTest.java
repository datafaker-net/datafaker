package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.company().name()).matches("[A-Za-z\\-&', ]+");
    }

    @Test
    void suffix() {
        assertThat(faker.company().suffix()).matches("[A-Za-z ]+");
    }

    @Test
    void industry() {
        assertThat(faker.company().industry()).matches("(\\w+([ ,&/-]{1,3})?){1,4}+");
    }

    @Test
    void buzzword() {
        assertThat(faker.company().buzzword()).matches("(\\w+[ /-]?){1,3}");
    }

    @Test
    void catchPhrase() {
        assertThat(faker.company().catchPhrase()).matches("(\\w+[ /-]?){1,9}");
    }

    @Test
    void bs() {
        assertThat(faker.company().bs()).matches("(\\w+[ /-]?){1,9}");
    }

    @Test
    void logo() {
        assertThat(faker.company().logo()).matches("https://pigment.github.io/fake-logos/logos/medium/color/\\d+\\.png");
    }

    @Test
    void profession() {
        assertThat(faker.company().profession()).matches("[a-z ]+");
    }

    @RepeatedTest(100)
    void url() {
        String regexp = "(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])";
        assertThat(faker.company().url()).matches(regexp);
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class NigeriaTest extends AbstractFakerTest{

    @Test
    public void places() {
        assertThat(faker.nigeria().places(), not(is(emptyOrNullString())));
    }


    @Test
    void food() {
        assertThat(faker.nigeria().food(), not(is(emptyOrNullString())));
    }

    @Test
    void names() {
        assertThat(faker.nigeria().name(), not(is(emptyOrNullString())));
    }

    @Test
    void schools() {
        assertThat(faker.nigeria().celebrities(), not(is(emptyOrNullString())));
    }

    @Test
    void celebrities() {
        assertThat(faker.nigeria().celebrities(), not(is(emptyOrNullString())));
    }
}

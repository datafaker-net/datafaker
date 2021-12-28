package com.github.javafaker;

import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChuckNorrisTest extends AbstractFakerTest {

    @Test
    public void testFact() {
        assertThat(faker.chuckNorris().fact(), not(isEmptyOrNullString()));
    }
}

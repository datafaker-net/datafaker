package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhotographyTest extends AbstractFakerTest {

    @Test
    void testAperture() {
        final String value = faker.photography().aperture();
        assertThat(value).startsWith("f");
    }

    @Test
    void testTerm() {
        final String value = faker.photography().term();
        assertNonNullOrEmpty(value);
    }

    @Test
    void brand() {
        final String value = faker.photography().brand();
        assertNonNullOrEmpty(value);
    }

    @Test
    void camera() {
        final String value = faker.photography().camera();
        assertNonNullOrEmpty(value);
    }

    @Test
    void lens() {
        final String value = faker.photography().lens();
        assertNonNullOrEmpty(value);
    }

    @Test
    void genre() {
        final String value = faker.photography().genre();
        assertNonNullOrEmpty(value);
    }

    @Test
    void imageTag() {
        final String value = faker.photography().imageTag();
        assertNonNullOrEmpty(value);
    }

    @RepeatedTest(7)
    void shutter() {
        final String value = faker.photography().shutter();
        assertThat(value).matches("\\d+/?\\d*");
    }

    @RepeatedTest(7)
    void iso() {
        final String value = faker.photography().iso();
        assertThat(value).matches("\\d+");
    }

    private void assertNonNullOrEmpty(String value) {
        assertThat(value)
            .isNotEmpty();
    }
}

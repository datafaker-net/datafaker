package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotographyTest extends AbstractFakerTest {

    @Test
    public void testAperture() {
        final String value = faker.photography().aperture();
        assertThat(value).startsWith("f");
    }

    @Test
    public void testTerm() {
        final String value = faker.photography().term();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void brand() {
        final String value = faker.photography().brand();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void camera() {
        final String value = faker.photography().camera();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void lens() {
        final String value = faker.photography().lens();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void genre() {
        final String value = faker.photography().genre();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void imageTag() {
        final String value = faker.photography().imageTag();
        assertNonNullOrEmpty(value);
    }

    @RepeatedTest(7)
    public void shutter() {
        final String value = faker.photography().shutter();
        assertThat(value).matches("\\d+/?\\d*");
    }

    @RepeatedTest(7)
    public void iso() {
        final String value = faker.photography().iso();
        assertThat(value).matches("\\d+");
    }

    private void assertNonNullOrEmpty(String value) {
        assertThat(value)
            .isNotEmpty();
    }
}

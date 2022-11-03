package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CompassTest extends BaseFakerTest<BaseFaker> {

    @ParameterizedTest
    @EnumSource(value = Compass.CompassPoint.class)
    void compassDirectionWordWithCompassPoint(Compass.CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).word()).isNotEmpty();
    }

    @Test
    void compassDirectionWordWithoutCompassPoint() {
        assertThat(faker.compass().word()).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(value = Compass.CompassPoint.class)
    void compassDirectionAbbreviationWithCompassPoint(Compass.CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).abbreviation()).isNotEmpty();
    }

    @Test
    void compassDirectionAbbreviationWithoutCompassPoint() {
        assertThat(faker.compass().abbreviation()).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(value = Compass.CompassPoint.class)
    void compassAzimuthWithCompassPoint(Compass.CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).azimuth()).isNotEmpty();
    }

    @Test
    void compassAzimuthWithoutCompassPoint() {
        assertThat(faker.compass().azimuth()).isNotEmpty();
    }
}

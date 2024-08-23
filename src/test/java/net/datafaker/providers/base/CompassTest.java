package net.datafaker.providers.base;

import net.datafaker.providers.base.Compass.CompassPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CompassTest extends BaseFakerTest<BaseFaker> {

    @ParameterizedTest
    @EnumSource(CompassPoint.class)
    void compassDirectionWordWithCompassPoint(CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).word()).isNotEmpty();
    }

    @Test
    void compassDirectionWordWithoutCompassPoint() {
        assertThat(faker.compass().word()).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(CompassPoint.class)
    void compassDirectionAbbreviationWithCompassPoint(CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).abbreviation()).isNotEmpty();
    }

    @Test
    void compassDirectionAbbreviationWithoutCompassPoint() {
        assertThat(faker.compass().abbreviation()).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(CompassPoint.class)
    void compassAzimuthWithCompassPoint(CompassPoint compassPointOfDirection) {
        assertThat(faker.compass().compassPoint(compassPointOfDirection).azimuth()).isNotEmpty();
    }

    @Test
    void compassAzimuthWithoutCompassPoint() {
        assertThat(faker.compass().azimuth()).isNotEmpty();
    }
}

package net.datafaker;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DurationTest extends AbstractFakerTest {
    final int DURATION_IS_EQUAL = 0;

    @Test
    void testDurationSeconds() {
        final long maxSeconds = 55;
        Duration randomDuration = faker.duration().atMostSeconds(maxSeconds);
        Duration lowerBound = Duration.ofSeconds(0);
        Duration upperBound = Duration.ofSeconds(maxSeconds);

        assertThat(randomDuration.compareTo(lowerBound)).isGreaterThanOrEqualTo(DURATION_IS_EQUAL);
        assertThat(randomDuration.compareTo(upperBound)).isLessThanOrEqualTo(DURATION_IS_EQUAL);
    }

    @Test
    void testDurationMinutes() {
        final long maxMins = 45;
        Duration randomDuration = faker.duration().atMostMinutes(maxMins);
        Duration lowerBound = Duration.ofMinutes(0);
        Duration upperBound = Duration.ofMinutes(maxMins);

        assertThat(randomDuration.compareTo(lowerBound)).isGreaterThanOrEqualTo(DURATION_IS_EQUAL);
        assertThat(randomDuration.compareTo(upperBound)).isLessThanOrEqualTo(DURATION_IS_EQUAL);
    }

    @Test
    void testDurationHours() {
        final long maxHours = 35;
        Duration randomDuration = faker.duration().atMostHours(maxHours);
        Duration lowerBound = Duration.ofHours(0);
        Duration upperBound = Duration.ofHours(maxHours);

        assertThat(randomDuration.compareTo(lowerBound)).isGreaterThanOrEqualTo(DURATION_IS_EQUAL);
        assertThat(randomDuration.compareTo(upperBound)).isLessThanOrEqualTo(DURATION_IS_EQUAL);
    }

    @Test
    void testDurationDays() {
        final long maxDays = 40;
        Duration randomDuration = faker.duration().atMostDays(maxDays);
        Duration lowerBound = Duration.ofDays(0);
        Duration upperBound = Duration.ofDays(maxDays);

        assertThat(randomDuration.compareTo(lowerBound)).isGreaterThanOrEqualTo(DURATION_IS_EQUAL);
        assertThat(randomDuration.compareTo(upperBound)).isLessThanOrEqualTo(DURATION_IS_EQUAL);
    }

}

package net.datafaker;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeTest extends AbstractFakerTest {

    @Test
    void testFutureTime() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long future = faker.time().future(1, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(future))
                .isAfter(now)
                .isBefore(now.plus(10, ChronoUnit.SECONDS));
        }
    }

    @Test
    void testFutureTimeWithMinimum() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long future = faker.time().future(5, 4, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(future))
                .isAfter(now)
                .isBefore(now.plus(10, ChronoUnit.SECONDS))
                .isAfter(now.plus(1, ChronoUnit.SECONDS));
        }
    }

    @Test
    void testPastTimeWithMinimum() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long past = faker.time().past(5, 4, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(past))
                .isBefore(now)
                .isAfter(now.minus(6, ChronoUnit.SECONDS))
                .isBefore(now.minus(2, ChronoUnit.SECONDS));
        }
    }

    @Test
    void testPastTime() {
        LocalTime now = LocalTime.now();
        long past = faker.time().past(100, ChronoUnit.SECONDS);
        assertThat(LocalTime.ofNanoOfDay(past)).isBefore(now);
    }

    @Test
    void testBetween() {
        LocalTime now = LocalTime.now();
        LocalTime then = now.plus(1, ChronoUnit.SECONDS);

        for (int i = 0; i < 1000; i++) {
            long time = faker.time().between(now, then);
            assertThat(LocalTime.ofNanoOfDay(time))
                .isBefore(then)
                .isAfter(now);
        }
    }

    @Test
    void testBetweenThenLargerThanNow() {
        LocalTime now = LocalTime.now();
        LocalTime then = now.plus(1, ChronoUnit.SECONDS);
        assertThatThrownBy(() -> faker.time().between(then, now))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid time range, the upper bound time is before the lower bound.");
    }

    @Test
    void futureWithMask() {
        String pattern = "mm:hh:ss";
        DateTimeFormatter.ofPattern(pattern).parse(faker.time().future(1, ChronoUnit.HOURS, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.time().future(20, 1, ChronoUnit.HOURS, pattern));
    }

    @Test
    void pastWithMask() {
        String pattern = "mm:hh:ss";
        DateTimeFormatter.ofPattern(pattern).parse(faker.time().past(1, ChronoUnit.MINUTES, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.time().past(20, 1, ChronoUnit.MILLIS, pattern));
    }
}

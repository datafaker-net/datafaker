package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeTest extends BaseFakerTest<BaseFaker> {

    private static final Pattern RE_TIME_BETWEEN = Pattern.compile("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]");
    private static final long NANOSECONDS_IN_DAY = 24L * 60 * 60 * 1000 * 1000_000L;
    private static final long NANOSECONDS_IN_MINUTE = 60 * 1000 * 1000_000L;

    @Test
    void testFutureTime() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long future = faker.time().future(1, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(future))
                .isAfter(now)
                .isBefore(now.plusSeconds(10));
        }
    }

    @Test
    void testFutureTimeWithMinimum() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long future = faker.time().future(5, 4, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(future))
                .isAfter(now)
                .isBefore(now.plusSeconds(10))
                .isAfter(now.plusSeconds(1));
        }
    }

    @Test
    void testPastTimeWithMinimum() {
        LocalTime now = LocalTime.now();
        for (int i = 0; i < 1000; i++) {
            long past = faker.time().past(5, 4, ChronoUnit.SECONDS);
            assertThat(LocalTime.ofNanoOfDay(past))
                .isBefore(now)
                .isAfter(now.minusSeconds(6))
                .isBefore(now.minusSeconds(2));
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
        LocalTime then = now.plusSeconds(1);

        for (int i = 0; i < 1000; i++) {
            long time = faker.time().between(now, then);
            assertThat(LocalTime.ofNanoOfDay(time))
                .isBefore(then)
                .isAfter(now);
        }
    }

    @Test
    void testBetweenWithSameLocalTime() {
        LocalTime now = LocalTime.now();

        long time = faker.time().between(now, now);
        assertThat(LocalTime.ofNanoOfDay(time)).isEqualTo(now);
    }

    @Test
    void testBetweenThenLargerThanNow() {
        LocalTime now = LocalTime.now();
        LocalTime then = now.plusSeconds(1);
        assertThatThrownBy(() -> faker.time().between(then, now))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid time range: the upper bound time (%s) is before the lower bound (%s)".formatted(now, then));
    }

    @RepeatedTest(10)
    void testBetweenWithMask() {
        String pattern = "HH:mm:ss";
        LocalTime now = LocalTime.ofNanoOfDay((long) (Math.random() * (NANOSECONDS_IN_DAY - NANOSECONDS_IN_MINUTE - 1)));
        LocalTime then = now.plusMinutes(1);

        String result = faker.time().between(now, then, pattern);
        assertThat(result).matches(RE_TIME_BETWEEN);
        TemporalAccessor timeBetween = DateTimeFormatter.ofPattern(pattern).parse(result);
        assertThat(timeBetween.query(LocalTime::from)).isAfter(now.minusSeconds(1));
        assertThat(timeBetween.query(LocalTime::from)).isBefore(then.plusSeconds(1));
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

package net.datafaker.providers.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeAndDateTest {
    private final Faker faker = new Faker();
    private final TimeAndDate timeAndDate = faker.timeAndDate();

    @RepeatedTest(100)
    void testFutureDate() {
        assertThat(timeAndDate.future()).isInTheFuture();
    }

    @RepeatedTest(100)
    void testFutureDateWithBounds() {
        Instant now = Instant.now();
        Instant future = timeAndDate.future(1, TimeUnit.SECONDS);
        assertThat(future).isBetween(now, now.plusSeconds(1));
    }

    @RepeatedTest(100)
    void testFutureDateWithBoundsFromGivenMoment() {
        Instant moment = Instant.now().minus(10, ChronoUnit.DAYS);
        Instant future = timeAndDate.future(15, TimeUnit.MINUTES, moment);
        assertThat(future).isBetween(moment, moment.plus(15, MINUTES));
    }

    @RepeatedTest(100)
    void testFutureDateWithMinimum() {
        Instant now = Instant.now();
        Instant future = timeAndDate.future(5, 4, TimeUnit.SECONDS);
        assertThat(future)
            .isBetween(now.plusMillis(3500), now.plusMillis(5500));
    }

    @RepeatedTest(100)
    void testPastDateWithMinimum() {
        final long now = System.currentTimeMillis();
        Instant past = timeAndDate.past(5, 4, TimeUnit.SECONDS);
        assertThat(past.toEpochMilli()).isLessThan(now)
            .isGreaterThan(now - 5500)
            .isLessThan(now - 3500);
    }

    @RepeatedTest(100)
    void testPastDateWithReferenceDate() {
        Instant now = Instant.now();
        Instant past = timeAndDate.past(1, TimeUnit.SECONDS, now);
        assertThat(past.toEpochMilli())
            .isLessThan(now.toEpochMilli())
            .isGreaterThan(now.toEpochMilli() - 1000);
    }

    @RepeatedTest(100)
    void testPastDate() {
        assertThat(timeAndDate.past()).isInThePast();
    }

    @RepeatedTest(100)
    void testPastDateWithBounds() {
        Instant now = Instant.now();
        Instant past = timeAndDate.past(100, TimeUnit.SECONDS);
        assertThat(past.toEpochMilli()).isLessThan(now.toEpochMilli());
    }

    @RepeatedTest(100)
    void testBetween() {
        Instant now = Instant.now();
        Instant then = Instant.now().plusMillis(1000);

        Instant date = timeAndDate.between(now, then);
        assertThat(date.toEpochMilli())
            .isLessThan(then.toEpochMilli())
            .isGreaterThanOrEqualTo(now.toEpochMilli());
    }

    @Test
    void testBetweenWithMaskReturningString() {
        Instant now = Instant.now();
        Instant then = Instant.now().plusMillis(1000);
        String pattern = "yyyy MM.dd mm:hh:ss";

        String date = timeAndDate.between(now, then, pattern);

        assertValidDate(date, pattern);
    }

    @Test
    void testBetweenThenLargerThanNow() {
        Instant now = Instant.now();
        Instant then = Instant.now().plusMillis(1000);

        assertThatThrownBy(() -> timeAndDate.between(then, now))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid date range: the upper bound date (%s) is before the lower bound (%s)".formatted(now, then));
    }

    @RepeatedTest(100)
    void testBirthday() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDate from = now.minusYears(TimeAndDate.DEFAULT_MIN_AGE).toLocalDate();
        final LocalDate to = now.minusYears(TimeAndDate.DEFAULT_MAX_AGE).toLocalDate();
        LocalDate birthday = timeAndDate.birthday();
        assertThat(birthday).isBetween(to, from);
    }

    @RepeatedTest(100)
    void testBirthdayWithAges() {
        LocalDate nw = LocalDate.now();
        final Number number = faker.number();
        int minAge = number.numberBetween(1, 99);
        int maxAge = number.numberBetween(minAge, 100);

        LocalDate from = nw.minusYears(maxAge);
        LocalDate to = nw.minusYears(minAge);

        LocalDate birthday = timeAndDate.birthday(minAge, maxAge);

        assertThat(birthday).isBetween(from, to);
    }

    @Test
    void birthdayWithMask() {
        String pattern = "yyyy MM.dd";
        String birthday = timeAndDate.birthday(1, 50, pattern);
        assertValidDate(birthday, pattern);
    }

    @Test
    void futureWithMask() {
        String pattern = "yyyy MM.dd mm:hh:ss";
        assertValidDate(timeAndDate.future(1, TimeUnit.HOURS, pattern), pattern);
        assertValidDate(timeAndDate.future(20, 1, TimeUnit.HOURS, pattern), pattern);
        assertValidDate(timeAndDate.future(20, TimeUnit.HOURS, Instant.now(), pattern), pattern);
        assertValidDate(timeAndDate.future(900, TimeUnit.DAYS, Instant.now(), pattern), pattern);
    }

    @Test
    void pastWithMask() {
        String pattern = "yyyy MM.dd mm:hh:ss";
        assertValidDate(timeAndDate.past(1, TimeUnit.DAYS, pattern), pattern);
        assertValidDate(timeAndDate.past(20, 1, TimeUnit.DAYS, pattern), pattern);
        assertValidDate(timeAndDate.past(1, TimeUnit.DAYS, Instant.now(), pattern), pattern);
    }

    @Test
    void periodTest() {
        Period maxPeriod = Period.of(3, 2, 1);
        Period minPeriod = Period.of(2, 1, 0);
        Period period = timeAndDate.period(minPeriod, maxPeriod);

        assertThat((period.getYears() * 12 + period.getMonths()) * 30 + period.getDays())
            .isBetween((minPeriod.getYears() * 12 + minPeriod.getMonths()) * 30 + minPeriod.getDays(),
                (maxPeriod.getYears() * 12 + maxPeriod.getMonths()) * 30 + maxPeriod.getDays());
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMinMax")
    void durationTest(long minValue, long maxValue, ChronoUnit unit) {
        Duration generated = timeAndDate.duration(minValue, maxValue, unit);
        Duration min = Duration.of(minValue, unit);
        Duration max = Duration.of(maxValue, unit);
        assertThat(generated).isGreaterThanOrEqualTo(min);
        if (minValue >= maxValue) {
            assertThat(generated)
                .as(() -> "Duration (%s) must be equal to max value if min (%s) == max (%s)".formatted(generated, min, max))
                .isEqualTo(max);
        } else {
            assertThat(generated)
                .as(() -> "Duration (%s) must be lower than max value (%s) (min value: %s)".formatted(generated, max, min))
                .isLessThan(max);
        }
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMaxOnly")
    void durationTest(long maxValue, ChronoUnit unit) {
        Duration generated = timeAndDate.duration(maxValue, unit);
        assertThat(generated)
            .as(() -> "Duration (%s) must be lower than max value (%s %s)".formatted(generated, maxValue, unit))
            .isLessThan(Duration.of(maxValue, unit));
    }

    @Test
    void durationIsZero_ifMaxIsZero() {
        assertThat(timeAndDate.duration(0, ChronoUnit.DAYS))
            .isEqualTo(Duration.of(0, ChronoUnit.DAYS));
    }

    @ParameterizedTest
    @MethodSource("generatePeriod")
    void maxLessThanMinPeriod(Period min, Period max) {
        assertThatThrownBy(() -> timeAndDate.period(min, max))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> generateDurationsWithMaxOnly() {
        return Stream.of(
            Arguments.of(100, ChronoUnit.DAYS),
            Arguments.of(456, ChronoUnit.HOURS),
            Arguments.of(43, ChronoUnit.MINUTES),
            Arguments.of(78, ChronoUnit.SECONDS),
            Arguments.of(786, ChronoUnit.MILLIS),
            Arguments.of(786, ChronoUnit.MICROS),
            Arguments.of(8729, ChronoUnit.NANOS)
        );
    }

    private static Stream<Arguments> generateDurationsWithMinMax() {
        return Stream.of(
            Arguments.of(123, 123, ChronoUnit.DAYS),
            Arguments.of(12, 123, ChronoUnit.HOURS),
            Arguments.of(15, 400, ChronoUnit.MINUTES),
            Arguments.of(65, 98, ChronoUnit.SECONDS),
            Arguments.of(76, 100, ChronoUnit.MILLIS),
            Arguments.of(879, 1030, ChronoUnit.MICROS),
            Arguments.of(879, 1030, ChronoUnit.NANOS),
            Arguments.of(0, Long.MAX_VALUE, ChronoUnit.NANOS),
            Arguments.of(Long.MIN_VALUE, 0, ChronoUnit.NANOS),
            Arguments.of(Long.MAX_VALUE - 1, Long.MAX_VALUE, ChronoUnit.NANOS),
            Arguments.of(Long.MIN_VALUE, Long.MAX_VALUE, ChronoUnit.NANOS)
        );
    }

    private static Stream<Arguments> generatePeriod() {
        return Stream.of(
            Arguments.of(Period.of(1, 1, 1), Period.of(0, 1, 1)),
            Arguments.of(Period.of(1, 1, 3), Period.of(1, 1, 2)),
            Arguments.of(Period.of(1, 2, 1), Period.of(1, 1, 1))
        );
    }

    private void assertValidDate(String date, String pattern) {
        assertThatCode(() -> parse(date, pattern)).doesNotThrowAnyException();
    }

    @CanIgnoreReturnValue
    private static LocalDate parse(String date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).parse(date).query(LocalDate::from);
    }
}

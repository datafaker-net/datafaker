package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRules;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author pmiklos
 */
class DateAndTimeTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testFutureDate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        DateAndTime date = faker.date();
        for (int i = 0; i < 1000; i++) {
            Date future = date.future(1, TimeUnit.SECONDS, now);
            assertThat(future.getTime()).isGreaterThan(now.getTime())
                .isLessThan(now.getTime() + 1000);
        }
    }

    @Test
    void testFutureDateWithMinimum() {
        final Date now = new Date();
        DateAndTime date = faker.date();
        for (int i = 0; i < 1000; i++) {
            Date future = date.future(5, 4, TimeUnit.SECONDS);
            assertThat(future.getTime()).isGreaterThan(now.getTime())
                .isLessThan(now.getTime() + 5500)
                .isGreaterThan(now.getTime() + 3500);
        }
    }

    @Test
    void testPastDateWithMinimum() {
        DateAndTime date = faker.date();
        for (int i = 0; i < 1000; i++) {
            final long now = System.currentTimeMillis();
            Date past = date.past(5, 4, TimeUnit.SECONDS);
            assertThat(past.getTime()).isLessThan(now)
                .isGreaterThan(now - 5500)
                .isLessThan(now - 3500);
        }
    }

    @Test
    void testPastDateWithReferenceDate() {
        Date now = new Date();
        DateAndTime date = faker.date();
        for (int i = 0; i < 1000; i++) {
            Date past = date.past(1, TimeUnit.SECONDS, now);
            assertThat(past.getTime()).isLessThan(now.getTime())
                .isGreaterThan(now.getTime() - 1000);
        }
    }

    @Test
    void testPastDate() {
        Date now = new Date();
        Date past = faker.date().past(100, TimeUnit.SECONDS);
        assertThat(past.getTime()).isLessThan(now.getTime());
    }

    @Test
    void testBetween() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp then = new Timestamp(System.currentTimeMillis() + 1000);
        DateAndTime dateAndTime = faker.date();
        for (int i = 0; i < 1000; i++) {
            Date date = dateAndTime.between(now, then);
            assertThat(date.getTime()).isLessThan(then.getTime())
                .isGreaterThanOrEqualTo(now.getTime());
        }
    }

    @Test
    void testBetweenWithMaskReturningString() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp then = new Timestamp(System.currentTimeMillis() + 1000);
        String pattern = "YYYY MM.dd mm:hh:ss";

        DateTimeFormatter.ofPattern(pattern).parse(faker.date().between(now, then, pattern));
    }

    @Test
    void testBetweenDateAsArgument() {
        faker.date().between(new Date(), new Date());
    }

    @Test
    void testBetweenThenLargerThanNow() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp then = new Timestamp(System.currentTimeMillis() + 1000);
        assertThatThrownBy(() -> faker.date().between(then, now))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid date range: the upper bound date (%s) is before the lower bound (%s)".formatted(now, then));
    }

    @Test
    void testBirthday() {
        final LocalDateTime localDate = LocalDateTime.now();
        final long to = localDate.minusYears(18).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        final long from = localDate.minusYears(65).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        final DateAndTime date = faker.date();
        for (int i = 0; i < 5000; i++) {
            Timestamp birthday = date.birthday();
            assertThat(birthday.getTime()).isLessThan(to)
                .isGreaterThanOrEqualTo(from);
        }
    }

    @Test
    void testBirthdayWithAges() {
        LocalDateTime nw = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        final Number number = faker.number();
        final DateAndTime date = faker.date();
        final ZoneRules rules = ZoneId.systemDefault().getRules();
        for (int i = 0; i < 5000; i++) {
            int minAge = number.numberBetween(1, 99);
            int maxAge = number.numberBetween(minAge, 100);

            LocalDateTime from = nw.minusYears(maxAge);
            LocalDateTime to = nw.minusYears(minAge);

            Timestamp birthday = date.birthday(minAge, maxAge);

            assertThat(birthday)
                .isBetween(Timestamp.from(from.toInstant(rules.getOffset(from))),
                    Timestamp.from(to.toInstant(rules.getOffset(to))), true, true);
        }
    }

    @Test
    void birthdayWithMask() {
        String pattern = "YYYY MM.dd";
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().birthday(1, 50, pattern));
    }

    @Test
    void futureWithMask() {
        String pattern = "YYYY MM.dd mm:hh:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        DateAndTime date = faker.date();
        dateTimeFormatter.parse(date.future(1, TimeUnit.HOURS, pattern));
        dateTimeFormatter.parse(date.future(20, 1, TimeUnit.HOURS, pattern));
        dateTimeFormatter.parse(date.future(20, TimeUnit.HOURS, new Date(), pattern));
    }

    @Test
    void pastWithMask() {
        String pattern = "YYYY MM.dd mm:hh:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        DateAndTime date = faker.date();
        dateTimeFormatter.parse(date.past(1, TimeUnit.DAYS, pattern));
        dateTimeFormatter.parse(date.past(20, 1, TimeUnit.DAYS, pattern));
        dateTimeFormatter.parse(date.past(1, TimeUnit.DAYS, new Date(), pattern));
    }

    @Test
    void periodTest() {
        Period maxPeriod = Period.of(3, 2, 1);
        Period minPeriod = Period.of(2, 1, 0);
        Period period = faker.date().period(minPeriod, maxPeriod);

        assertThat((period.getYears() * 12 + period.getMonths()) * 30 + period.getDays())
            .isBetween((minPeriod.getYears() * 12 + minPeriod.getMonths()) * 30 + minPeriod.getDays(),
                (maxPeriod.getYears() * 12 + maxPeriod.getMonths()) * 30 + maxPeriod.getDays());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "month", "year", "week"})
    void invalidDuration(String invalid) {
        assertThatThrownBy(() -> faker.date().duration(faker.random().nextLong(), invalid))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMinMax")
    void durationTest(long minValue, long maxValue, ChronoUnit unit) {
        Duration generated = faker.date().duration(minValue, maxValue, unit);
        Duration min = Duration.of(minValue, unit);
        Duration max = Duration.of(maxValue, unit);
        assertThat(min)
            .as("Duration must be equal or greater than min value")
            .isLessThanOrEqualTo(generated);
        assertThat(max.compareTo(generated) > 0 || minValue >= maxValue && max.equals(generated))
            .as("Duration must be lower than max value").isTrue();
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMaxOnly")
    void durationTest(long maxValue, ChronoUnit unit) {
        Duration generated = faker.date().duration(maxValue, unit);
        Duration max = Duration.of(maxValue, unit);
        assertThat(max.compareTo(generated) > 0 || maxValue == 0)
            .as("Duration must be lower than max value")
            .isTrue();
    }

    @ParameterizedTest
    @MethodSource("generateDurationsFromStringWithMinMax")
    void durationTest(long minValue, long maxValue, String unit) {
        Duration generated = faker.date().duration(minValue, maxValue, unit);
        Duration min = Duration.of(minValue, DateAndTime.str2durationUnit(unit));
        Duration max = Duration.of(maxValue, DateAndTime.str2durationUnit(unit));
        assertThat(min).as("Duration must be equal or greater than min value").isLessThanOrEqualTo(generated);
        assertThat(max.compareTo(generated) > 0 || minValue >= maxValue && max.equals(generated))
            .as("Duration must be lower than max value")
            .isTrue();
    }

    @ParameterizedTest
    @MethodSource("generateDurationsFromStringWithMaxOnly")
    void durationTest(long maxValue, String unit) {
        Duration generated = faker.date().duration(maxValue, unit);
        Duration max = Duration.of(maxValue, DateAndTime.str2durationUnit(unit));
        assertThat(max.compareTo(generated) > 0 || maxValue == 0).as("Duration must be lower than max value").isTrue();
    }

    @ParameterizedTest
    @MethodSource("generatePeriod")
    void maxLessThanMinPeriod(Period min, Period max) {
        assertThatThrownBy(() -> faker.date().period(min, max))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> generateDurationsFromStringWithMaxOnly() {
        return Stream.of(
            Arguments.of(0, "days"),
            Arguments.of(100, "days"),
            Arguments.of(123, "DAY"),
            Arguments.of(456, "HOUR"),
            Arguments.of(1234, "hours"),
            Arguments.of(43, "minutes"),
            Arguments.of(78, "minute"),
            Arguments.of(56, "seconds"),
            Arguments.of(34, "second"),
            Arguments.of(786, "millis"),
            Arguments.of(879, "milli"),
            Arguments.of(8729, "nano"),
            Arguments.of(8739, "nanos")
        );
    }

    private static Stream<Arguments> generateDurationsFromStringWithMinMax() {
        return Stream.of(
            Arguments.of(123, 123, "days"),
            Arguments.of(12, 123, "days"),
            Arguments.of(21, 32, "DAY"),
            Arguments.of(45, 100, "HOUR"),
            Arguments.of(23, 100, "hours"),
            Arguments.of(15, 400, "minutes"),
            Arguments.of(14, 500, "minute"),
            Arguments.of(32, 54, "seconds"),
            Arguments.of(65, 98, "second"),
            Arguments.of(76, 100, "millis"),
            Arguments.of(87, 100, "milli"),
            Arguments.of(76, 100, "micros"),
            Arguments.of(87, 100, "micro"),
            Arguments.of(874, 1300, "nano"),
            Arguments.of(879, 1030, "nanos")
        );
    }

    private static Stream<Arguments> generateDurationsWithMaxOnly() {
        return Stream.of(
            Arguments.of(0, ChronoUnit.DAYS),
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
            Arguments.of(879, 1030, ChronoUnit.NANOS)
        );
    }

    private static Stream<Arguments> generatePeriod() {
        return Stream.of(
            Arguments.of(Period.of(1, 1, 1), Period.of(0, 1, 1)),
            Arguments.of(Period.of(1, 1, 3), Period.of(1, 1, 2)),
            Arguments.of(Period.of(1, 2, 1), Period.of(1, 1, 1))
        );
    }
}

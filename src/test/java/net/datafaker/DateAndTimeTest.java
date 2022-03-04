package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author pmiklos
 */
public class DateAndTimeTest extends AbstractFakerTest {

    @Test
    public void testFutureDate() {
        Date now = new Date();

        for (int i = 0; i < 1000; i++) {
            Date future = faker.date().future(1, TimeUnit.SECONDS, now);
            assertThat("past date", future.getTime(), greaterThan(now.getTime()));
            assertThat("future date over range", future.getTime(), lessThan(now.getTime() + 1000));
        }
    }

    @Test
    public void testFutureDateWithMinimum() {
        final Date now = new Date();
        for (int i = 0; i < 1000; i++) {
            Date future = faker.date().future(5, 4, TimeUnit.SECONDS);
            assertThat("past date", future.getTime(), greaterThan(now.getTime()));
            assertThat("future date over range", future.getTime(), lessThan(now.getTime() + 5500));
            assertThat("future date under minimum range", future.getTime(), greaterThan(now.getTime() + 3500));
        }
    }

    @Test
    public void testPastDateWithMinimum() {
        final Date now = new Date();
        for (int i = 0; i < 1000; i++) {
            Date past = faker.date().past(5, 4, TimeUnit.SECONDS);
            assertThat("future date", past.getTime(), lessThan(now.getTime()));
            assertThat("past date over range", past.getTime(), greaterThan(now.getTime() - 5500));
            assertThat("past date under minimum range", past.getTime(), lessThan(now.getTime() - 3500));
        }
    }

    @Test
    public void testPastDateWithReferenceDate() {
        Date now = new Date();

        for (int i = 0; i < 1000; i++) {
            Date past = faker.date().past(1, TimeUnit.SECONDS, now);
            assertThat("past date", past.getTime(), lessThan(now.getTime()));
            assertThat("past date over range", past.getTime(), greaterThan(now.getTime() - 1000));
        }
    }

    @Test
    public void testPastDate() {
        Date now = new Date();
        Date past = faker.date().past(100, TimeUnit.SECONDS);
        assertThat("past date is in the past", past.getTime(), lessThan(now.getTime()));
    }

    @Test
    public void testBetween() {
        Date now = new Date();
        Date then = new Date(now.getTime() + 1000);

        for (int i = 0; i < 1000; i++) {
            Date date = faker.date().between(now, then);
            assertThat("after upper bound", date.getTime(), lessThan(then.getTime()));
            assertThat("before lower bound", date.getTime(), greaterThanOrEqualTo(now.getTime()));
        }
    }

    @Test
    public void testBetweenThenLargerThanNow() {
        try {
            Date now = new Date();
            Date then = new Date(now.getTime() + 1000);
            faker.date().between(then, now);
            fail("Should be exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid date range, the upper bound date is before the lower bound.", e.getMessage());
        }
    }

    @Test
    public void testBirthday() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        long from = new GregorianCalendar(currentYear - 65, currentMonth, currentDay).getTime().getTime();
        long to = new GregorianCalendar(currentYear - 18, currentMonth, currentDay).getTime().getTime();

        for (int i = 0; i < 5000; i++) {
            Date birthday = faker.date().birthday();
            assertThat("birthday is after upper bound", birthday.getTime(), lessThan(to));
            assertThat("birthday is before lower bound", birthday.getTime(), greaterThanOrEqualTo(from));
        }
    }

    @Test
    public void testBirthdayWithAges() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 5000; i++) {
            int minAge = faker.number().numberBetween(1, 99);
            int maxAge = faker.number().numberBetween(minAge, 100);

            long from = new GregorianCalendar(currentYear - maxAge, currentMonth, currentDay).getTime().getTime();
            long to = new GregorianCalendar(currentYear - minAge, currentMonth, currentDay).getTime().getTime();

            Date birthday = faker.date().birthday(minAge, maxAge);
            assertThat("birthday is after upper bound", birthday.getTime(), lessThanOrEqualTo(to));
            assertThat("birthday is before lower bound", birthday.getTime(), greaterThanOrEqualTo(from));
        }
    }

    @Test
    public void birthdayWithMask() {
        String pattern = "YYYY MM.dd";
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().birthday(1, 50, pattern));
    }

    @Test
    public void futureWithMask() {
        String pattern = "YYYY MM.dd mm:hh:ss";
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().future(1, TimeUnit.HOURS, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().future(20, 1, TimeUnit.HOURS, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().future(20, TimeUnit.HOURS, new Date(), pattern));
    }

    @Test
    public void pastWithMask() {
        String pattern = "YYYY MM.dd mm:hh:ss";
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().past(1, TimeUnit.DAYS, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().past(20, 1, TimeUnit.DAYS, pattern));
        DateTimeFormatter.ofPattern(pattern).parse(faker.date().past(1, TimeUnit.DAYS, new Date(), pattern));
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "month", "year", "week"})
    public void invalidDuration(String invalid) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> faker.date().duration(faker.random().nextLong(), invalid));
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMinMax")
    public void durationTest(long minValue, long maxValue, ChronoUnit unit) {
        Duration generated = faker.date().duration(minValue, maxValue, unit);
        Duration min = Duration.of(minValue, unit);
        Duration max = Duration.of(maxValue, unit);
        assertThat("Duration must be equal or greater than min value", min.compareTo(generated) <= 0);
        assertThat("Duration must be lower than max value",
            max.compareTo(generated) > 0 || minValue >= maxValue && max.equals(generated));
    }

    @ParameterizedTest
    @MethodSource("generateDurationsWithMaxOnly")
    public void durationTest(long maxValue, ChronoUnit unit) {
        Duration generated = faker.date().duration(maxValue, unit);
        Duration max = Duration.of(maxValue, unit);
        assertThat("Duration must be lower than max value", max.compareTo(generated) > 0 || maxValue == 0);
    }

    @ParameterizedTest
    @MethodSource("generateDurationsFromStringWithMinMax")
    public void durationTest(long minValue, long maxValue, String unit) {
        Duration generated = faker.date().duration(minValue, maxValue, unit);
        Duration min = Duration.of(minValue, DateAndTime.str2unit(unit));
        Duration max = Duration.of(maxValue, DateAndTime.str2unit(unit));
        assertThat("Duration must be equal or greater than min value", min.compareTo(generated) <= 0);
        assertThat("Duration must be lower than max value",
            max.compareTo(generated) > 0 || minValue >= maxValue && max.equals(generated));
    }

    @ParameterizedTest
    @MethodSource("generateDurationsFromStringWithMaxOnly")
    public void durationTest(long maxValue, String unit) {
        Duration generated = faker.date().duration(maxValue, unit);
        Duration max = Duration.of(maxValue, DateAndTime.str2unit(unit));
        assertThat("Duration must be lower than max value", max.compareTo(generated) > 0 || maxValue == 0);
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
}

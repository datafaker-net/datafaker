package net.datafaker.providers.base;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * A generator of random times and dates.
 * <p>
 * This class is similar to {@link DateAndTime}, but migrated to the newer Java 8+ Time API.
 *
 * @since 2.3.0
 */
@SuppressWarnings("removal")
public class TimeAndDate extends AbstractProvider<BaseProviders> {

    static final int DEFAULT_MIN_AGE = 18;
    static final int DEFAULT_MAX_AGE = 65;

    protected TimeAndDate(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a future date from now.
     */
    public Instant future() {
        long FIFTY_YEARS = TimeUnit.DAYS.toMillis(18262);
        return future(faker.number().numberBetween(1, FIFTY_YEARS), MILLISECONDS);
    }

    /**
     * Generates a future date from now.
     *
     * @param atMost at most this amount of time ahead from now exclusive.
     * @param unit   the time unit.
     * @return a future date from now.
     */
    public Instant future(long atMost, TimeUnit unit) {
        Instant aBitLaterThanNow = Instant.now().plusMillis(1);
        return future(atMost, unit, aBitLaterThanNow);
    }

    /**
     * Generates and converts to string representation a future date from now.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param unit    the time unit.
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a future date from now.
     */
    public String future(long atMost, TimeUnit unit, String pattern) {
        return formatInstant(future(atMost, unit), pattern);
    }

    /**
     * Generates a future date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @return a future date from now, with a minimum time.
     */
    public Instant future(long atMost, long minimum, TimeUnit unit) {
        Instant minimumDate = Instant.now().plus(minimum, unit.toChronoUnit());
        return future(atMost - minimum, unit, minimumDate);
    }

    /**
     * Generates and converts to string representation
     * of a future date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a future date from now, with a minimum time.
     */
    public String future(long atMost, long minimum, TimeUnit unit, String pattern) {
        return formatInstant(future(atMost, minimum, unit), pattern);
    }

    /**
     * Generates a future date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time ahead to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the future date relative to this date.
     * @return a future date relative to {@code referenceDate}.
     */
    public Instant future(long atMost, TimeUnit unit, Instant referenceDate) {
        long upperBoundMillis = unit.toMillis(atMost);
        long futureMillis = referenceDate.toEpochMilli() + 1 + faker.random().nextLong(upperBoundMillis - 1);
        return Instant.ofEpochMilli(futureMillis);
    }

    /**
     * Generates and converts to string representation
     * a future date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time ahead to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the future date relative to this date.
     * @param pattern       date time pattern to convert to string.
     * @return a string representation of a future date relative to {@code referenceDate}.
     */
    public String future(long atMost, TimeUnit unit, Instant referenceDate, String pattern) {
        return formatInstant(future(atMost, unit, referenceDate), pattern);
    }

    /**
     * Generates a past date from now.
     */
     public Instant past() {
         long FIFTY_YEARS = TimeUnit.DAYS.toMillis(18262);
         return past(faker.number().numberBetween(1, FIFTY_YEARS), MILLISECONDS);
    }

    /**
     * Generates a past date from now.
     *
     * @param atMost at most this amount of time earlier from now exclusive.
     * @param unit   the time unit.
     * @return a past date from now.
     */
    public Instant past(long atMost, TimeUnit unit) {
        Instant aBitEarlierThanNow = Instant.now().minusMillis(1);
        return past(atMost, unit, aBitEarlierThanNow);
    }

    /**
     * Generates a string representation of a past date from now.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param unit    the time unit.
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a past date from now.
     */
    public String past(long atMost, TimeUnit unit, String pattern) {
        return formatInstant(past(atMost, unit), pattern);
    }

    /**
     * Generates a past date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @return a past date from now.
     */
    public Instant past(long atMost, long minimum, TimeUnit unit) {
        Instant minimumDate = Instant.now().minusMillis(unit.toMillis(minimum));
        return past(atMost - minimum, unit, minimumDate);
    }

    /**
     * Generates and converts to string representation a past date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a past date from now, with a minimum time.
     */
    public String past(long atMost, long minimum, TimeUnit unit, String pattern) {
        return formatInstant(past(atMost, minimum, unit), pattern);
    }

    /**
     * Generates a past date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time past to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the past date relative to this date.
     * @return a past date relative to {@code referenceDate}.
     */
    public Instant past(long atMost, TimeUnit unit, Instant referenceDate) {
        long upperBoundMillis = unit.toMillis(atMost);
        long pastMillis = referenceDate.toEpochMilli() - 1 - faker.random().nextLong(upperBoundMillis - 1);
        return Instant.ofEpochMilli(pastMillis);
    }
    /**
     * Generates a string representation of a past date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time past to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the past date relative to this date.
     * @param pattern       date time pattern to convert to string.
     * @return a string representation of a past date relative to {@code referenceDate}.
     */
    public String past(long atMost, TimeUnit unit, Instant referenceDate, String pattern) {
        return formatInstant(past(atMost, unit, referenceDate), pattern);
    }

    /**
     * Generates a random date between two dates.
     *
     * @param from the lower bound inclusive
     * @param to   the upper bound exclusive
     * @return a random date between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} date represents an earlier date than {@code from} date.
     */
    public Instant between(Instant from, Instant to) throws IllegalArgumentException {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Invalid date range: the upper bound date (%s) is before the lower bound (%s)".formatted(to, from));
        }

        if (from.equals(to)) {
            return from;
        }

        long offsetMillis = faker.random().nextLong(to.toEpochMilli() - from.toEpochMilli());
        return from.plusMillis(offsetMillis);
    }

    /**
     * Generates a string representation of a random date between two dates.
     *
     * @param from    the lower bound inclusive
     * @param to      the upper bound exclusive
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a random date between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} date represents an earlier date than {@code from} date.
     */
    public String between(Instant from, Instant to, String pattern) throws IllegalArgumentException {
        return formatInstant(between(from, to), pattern);
    }

    /**
     * Generates a random birthday between 65 and 18 years ago from now.
     *
     * @return a random birthday between 65 and 18 years ago from now.
     */
    public LocalDate birthday() {
        return birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE);
    }

    /**
     * Generates a string representation of a random birthday between 65 and 18 years ago from now.
     *
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a random birthday between 65 and 18 years ago from now.
     */
    public String birthday(String pattern) {
        return formatInstant(birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE), pattern);
    }

    /**
     * Generates a random birthday between two ages from now.
     *
     * @param minAge the minimal age
     * @param maxAge the maximal age
     * @return a random birthday between {@code minAge} and {@code maxAge} years ago from now.
     * Negative {@code minAge} and {@code maxAge} are supported.
     */
    public LocalDate birthday(int minAge, int maxAge) {
        LocalDate localDate = LocalDate.now();
        LocalDate from = localDate.minusYears(maxAge);
        if (minAge == maxAge) {
            return from;
        } else {
            long start = from.toEpochDay();
            long stop = localDate.minusYears(minAge).toEpochDay();

            return LocalDate.ofEpochDay(faker.random().nextLong(start, stop));
        }
    }

    /**
     * Generates and converts to string representation a random birthday between two ages from now.
     *
     * @param minAge  the minimal age
     * @param maxAge  the maximal age
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a random birthday between {@code minAge} and {@code maxAge} years ago from now.
     * @throws IllegalArgumentException if the {@code maxAge} is lower than {@code minAge}.
     */
    public String birthday(int minAge, int maxAge, String pattern) {
        return formatInstant(birthday(minAge, maxAge), pattern);
    }

    /**
     * Generates a random Duration lower than max.
     *
     * @param max  the maximum value
     * @param unit the temporal unit (day or shorter than a day)
     * @return a random Duration lower than {@code max}.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    public Duration duration(long max, ChronoUnit unit) {
        return duration(0, max, unit);
    }

    /**
     * Generates a random Duration between min and max.
     *
     * @param min  the minimum value
     * @param max  the maximum value
     * @param unit the temporal unit (day or shorter than a day)
     * @return a random Duration between {@code min} inclusive and {@code max} exclusive if {@code max} greater {@code min}.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    public Duration duration(long min, long max, ChronoUnit unit) {
        return Duration.of(faker.random().nextLong(min, max), unit);
    }

    /**
     * Generates a random Period between min and max.
     *
     * @param min the minimal value
     * @param max the maximum value
     * @return a random Period between {@code min} inclusive and {@code max} inclusive if {@code max} greater {@code min}.
     * @throws IllegalArgumentException if the {@code min} is greater than {@code max}.
     */
    public Period period(Period min, Period max) {
        if (max.minus(min).isNegative()) {
            throw new IllegalArgumentException("Max period(" + max + ") should be not less than min (" + min + ")");
        }
        return Period.of(
            faker.random().nextInt(min.getYears(), max.getYears()),
            faker.random().nextInt(min.getMonths(), max.getMonths()),
            faker.random().nextInt(min.getDays(), max.getDays()));
    }

    private static String formatInstant(TemporalAccessor instant, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
            .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }
}

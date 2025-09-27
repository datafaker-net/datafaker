package net.datafaker.providers.base;


import net.datafaker.annotations.InternalApi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A generator of random dates.
 *
 * @author pmiklos
 * @since 0.8.0
 * @deprecated Use {@link TimeAndDate} instead.
 */
@Deprecated(since = "2.3.0", forRemoval = true)
public class DateAndTime extends AbstractProvider<BaseProviders> {
    private static final int DEFAULT_MIN_AGE = 18;
    private static final int DEFAULT_MAX_AGE = 65;
    public static final long DAYS_NANOS = TimeUnit.DAYS.toNanos(1L);

    protected DateAndTime(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a future date from now.
     *
     * @param atMost at most this amount of time ahead from now exclusive.
     * @param unit   the time unit.
     * @return a future date from now.
     */
    public Timestamp future(int atMost, TimeUnit unit) {
        Timestamp aBitLaterThanNow = new Timestamp(System.currentTimeMillis() + 1);
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
    public String future(int atMost, TimeUnit unit, String pattern) {
        return toString(future(atMost, unit), pattern);
    }

    /**
     * Generates a future date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @return a future date from now, with a minimum time.
     */
    public Timestamp future(int atMost, int minimum, TimeUnit unit) {
        Timestamp minimumDate = new Timestamp(System.currentTimeMillis() + unit.toMillis(minimum));
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
    public String future(int atMost, int minimum, TimeUnit unit, String pattern) {
        return toString(future(atMost, minimum, unit), pattern);
    }

    /**
     * Generates a future date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time ahead to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the future date relative to this date.
     * @return a future date relative to {@code referenceDate}.
     */
    public <T extends Date> T future(int atMost, TimeUnit unit, T referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis += 1 + faker.random().nextLong(upperBound - 1);

        return (T) new Timestamp(futureMillis);
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
    public <T extends Date> String future(int atMost, TimeUnit unit, T referenceDate, String pattern) {
        return toString(future(atMost, unit, referenceDate), pattern);
    }

    /**
     * Generates a past date from now.
     *
     * @param atMost at most this amount of time earlier from now exclusive.
     * @param unit   the time unit.
     * @return a past date from now.
     */
    public Timestamp past(int atMost, TimeUnit unit) {
        Timestamp aBitEarlierThanNow = new Timestamp(System.currentTimeMillis() - 1);
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
    public String past(int atMost, TimeUnit unit, String pattern) {
        return toString(past(atMost, unit), pattern);
    }

    /**
     * Generates a past date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @return a past date from now.
     */
    public Timestamp past(int atMost, int minimum, TimeUnit unit) {
        Timestamp minimumDate = new Timestamp(System.currentTimeMillis() - unit.toMillis(minimum));
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
    public String past(int atMost, int minimum, TimeUnit unit, String pattern) {
        return toString(past(atMost, minimum, unit), pattern);
    }

    /**
     * Generates a past date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time past to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the past date relative to this date.
     * @return a past date relative to {@code referenceDate}.
     */
    public <T extends Date> T past(int atMost, TimeUnit unit, T referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis -= 1 + faker.random().nextLong(upperBound - 1);

        return (T) new Timestamp(futureMillis);
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
    public String past(int atMost, TimeUnit unit, Date referenceDate, String pattern) {
        return toString(past(atMost, unit, referenceDate), pattern);
    }

    /**
     * Generates a random date between two dates.
     *
     * @param from the lower bound inclusive
     * @param to   the upper bound exclusive
     * @return a random date between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} date represents an earlier date than {@code from} date.
     */
    public <T extends Date> T between(T from, T to) throws IllegalArgumentException {
        if (to.before(from)) {
            throw new IllegalArgumentException("Invalid date range: the upper bound date (%s) is before the lower bound (%s)"
                .formatted(to, from));
        }

        if (from.equals(to)) {
            return from;
        }

        long offsetMillis = faker.random().nextLong(to.getTime() - from.getTime());
        return (T) new Timestamp(from.getTime() + offsetMillis);
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
    public <T extends Date> String between(T from, T to, String pattern) throws IllegalArgumentException {
        return toString(between(from, to), pattern);
    }

    /**
     * Generates a random birthday between 65 and 18 years ago from now.
     *
     * @return a random birthday between 65 and 18 years ago from now.
     */
    public Timestamp birthday() {
        return birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE);
    }

    /**
     * Generates a random birthday between 65 and 18 years ago from now as LocalDate.
     *
     * @return a random birthday between 65 and 18 years ago from now.
     */
    public LocalDate birthdayLocalDate() {
        return birthdayLocalDate(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE);
    }

    /**
     * Generates a string representation of a random birthday between 65 and 18 years ago from now.
     *
     * @param pattern date time pattern to convert to string.
     * @return a string representation of a random birthday between 65 and 18 years ago from now.
     */
    public String birthday(String pattern) {
        return toString(birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE), pattern);
    }

    /**
     * Generates a random birthday between two ages from now.
     *
     * @param minAge the minimal age
     * @param maxAge the maximal age
     * @return a random birthday between {@code minAge} and {@code maxAge} years ago from now.
     * Negative {@code minAge} and {@code maxAge} are supported.
     */
    public Timestamp birthday(int minAge, int maxAge) {
        final LocalDate localDate = LocalDate.now();
        final LocalDate from = localDate.minusYears(maxAge);
        if (minAge == maxAge) {
            return Timestamp.valueOf(LocalDateTime.of(from, LocalTime.MIDNIGHT));
        }
        final long start = from.toEpochDay();
        final long stop = localDate.minusYears(minAge).toEpochDay();
        final LocalDate date = LocalDate.ofEpochDay(faker.random().nextLong(start, stop));
        return Timestamp.valueOf(
            LocalDateTime.of(date, LocalTime.ofNanoOfDay(faker.number().numberBetween(0, DAYS_NANOS))));
    }

    /**
     * Generates a random birthday between two ages from now as LocalDate.
     *
     * @param minAge the minimal age
     * @param maxAge the maximal age
     * @return a random birthday between {@code minAge} and {@code maxAge} years ago from now.
     * Negative {@code minAge} and {@code maxAge} are supported.
     */
    public LocalDate birthdayLocalDate(int minAge, int maxAge) {
        final LocalDate localDate = LocalDate.now();
        final LocalDate from = localDate.minusYears(maxAge);
        if (minAge == maxAge) {
            return from;
        }
        final long start = from.toEpochDay();
        final long stop = localDate.minusYears(minAge).toEpochDay();
        return LocalDate.ofEpochDay(faker.random().nextLong(start, stop));
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
        return toString(birthday(minAge, maxAge), pattern);
    }

    /**
     * Generates a random Duration lower than max.
     *
     * @param max  the maximum value (exclusive in most cases)
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
     * @param min  the minimum value (inclusive)
     * @param max  the maximum value (exclusive in most cases)
     * @param unit the temporal unit (day or shorter than a day)
     * @return a random Duration between {@code min} inclusive and {@code max} exclusive if {@code max} greater {@code min}.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    public Duration duration(long min, long max, ChronoUnit unit) {
        return generateDuration(faker.random().nextLong(min, max), unit);
    }

    /**
     * Generates a random Duration lower than max.
     *
     * @param max  the maximum value
     * @param unit the temporal unit (day or shorter than a day)
     * @return a random Duration lower than {@code max}.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    public Duration duration(long max, String unit) {
        return duration(0, max, str2durationUnit(unit));
    }

    /**
     * Generates a random Duration between min and max.
     *
     * @param min  the minimal value
     * @param max  the maximum value
     * @param unit the temporal unit (day or shorter than a day)
     * @return a random Duration between {@code min} inclusive and {@code max} exclusive if {@code max} greater {@code min}.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    public Duration duration(long min, long max, String unit) {
        return generateDuration(faker.random().nextLong(min, max), str2durationUnit(unit));
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

    /**
     * Utility method to convert string to ChronoUnit.
     *
     * @param unit the temporal unit (day or shorter than a day)
     * @return converts unit to ChronoUnit.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    @InternalApi
    static ChronoUnit str2durationUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Illegal duration unit '" + unit + "'");
        }
        return switch (unit.toUpperCase(Locale.ROOT)) {
            case "NANO", "NANOS" -> ChronoUnit.NANOS;
            case "MICRO", "MICROS" -> ChronoUnit.MICROS;
            case "MILLI", "MILLIS" -> ChronoUnit.MILLIS;
            case "SECOND", "SECONDS" -> ChronoUnit.SECONDS;
            case "MINUTE", "MINUTES" -> ChronoUnit.MINUTES;
            case "HOUR", "HOURS" -> ChronoUnit.HOURS;
            case "DAY", "DAYS" -> ChronoUnit.DAYS;
            default -> throw new IllegalArgumentException("Illegal duration unit '" + unit + "'");
        };
    }

    private Duration generateDuration(long value, ChronoUnit unit) {
        return Duration.of(value, unit);
    }

    private String toString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}

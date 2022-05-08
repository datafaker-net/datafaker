package net.datafaker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A generator of random dates.
 *
 * @author pmiklos
 * @since 0.8.0
 */
public class DateAndTime {
    private static final int DEFAULT_MIN_AGE = 18;
    private static final int DEFAULT_MAX_AGE = 65;

    private final Faker faker;

    protected DateAndTime(Faker faker) {
        this.faker = faker;
    }

    /**
     * Generates a future date from now. Note that there is a 1 second slack to avoid generating a past date.
     *
     * @param atMost at most this amount of time ahead from now exclusive.
     * @param unit   the time unit.
     * @return a future date from now.
     */
    public Timestamp future(int atMost, TimeUnit unit) {
        Timestamp aBitLaterThanNow = new Timestamp(System.currentTimeMillis() + 1000);
        return future(atMost, unit, aBitLaterThanNow);
    }

    /**
     * Generates and converts to string representation a future date from now.
     * Note that there is a 1 second slack to avoid generating a past date.
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
    public Timestamp future(int atMost, TimeUnit unit, Date referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis += 1 + faker.random().nextLong(upperBound - 1);

        return new Timestamp(futureMillis);
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
    public String future(int atMost, TimeUnit unit, Date referenceDate, String pattern) {
        return toString(future(atMost, unit, referenceDate), pattern);
    }

    /**
     * Generates a past date from now. Note that there is a 1 second slack added.
     *
     * @param atMost at most this amount of time earlier from now exclusive.
     * @param unit   the time unit.
     * @return a past date from now.
     */
    public Timestamp past(int atMost, TimeUnit unit) {
        Timestamp aBitEarlierThanNow = new Timestamp(System.currentTimeMillis() - 1000);
        return past(atMost, unit, aBitEarlierThanNow);
    }

    /**
     * Generates a string representation of a past date from now.
     * Note that there is a 1 second slack added.
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
    public Timestamp past(int atMost, TimeUnit unit, Date referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis -= 1 + faker.random().nextLong(upperBound - 1);

        return new Timestamp(futureMillis);
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
    public Timestamp between(Timestamp from, Timestamp to) throws IllegalArgumentException {
        if (to.before(from)) {
            throw new IllegalArgumentException("Invalid date range, the upper bound date is before the lower bound.");
        }

        if (from.equals(to)) {
            return from;
        }

        long offsetMillis = faker.random().nextLong(to.getTime() - from.getTime());
        return new Timestamp(from.getTime() + offsetMillis);
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
    public String between(Timestamp from, Timestamp to, String pattern) throws IllegalArgumentException {
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
     * @throws IllegalArgumentException if the {@code maxAge} is lower than {@code minAge}.
     */
    public Timestamp birthday(int minAge, int maxAge) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Calendar from = new GregorianCalendar(currentYear - maxAge, currentMonth, currentDay);
        Calendar to = new GregorianCalendar(currentYear - minAge, currentMonth, currentDay);

        return between(new Timestamp(from.getTime().getTime()), new Timestamp(to.getTime().getTime()));
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
     * @param min  the maximum value
     * @param max  the minimal value
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
     * @param min  the minimal value
     * @param max  the maximum value
     * @return a random Period between {@code min} inclusive and {@code max} inclusive if {@code max} greater {@code min}.
     * @throws IllegalArgumentException if the {@code min} is greater than {@code max}.
     */
    public Period period(Period min, Period max) {
        if (max.minus(min).isNegative()) {
            throw new IllegalArgumentException("Max period(" + max + ") should be not less than min (" + min + ")");
        }
        return Period.of(
            faker.random().nextInt(min.getYears(), max.getYears() + 1),
            faker.random().nextInt(min.getMonths(), max.getMonths() + 1),
            faker.random().nextInt(min.getDays(), max.getDays() + 1));
    }

    /**
     * Utility method to convert string to ChronoUnit.
     *
     * @param unit the temporal unit (day or shorter than a day)
     * @return converts unit to ChronoUnit.
     * @throws IllegalArgumentException if the {@code unit} is invalid.
     */
    static ChronoUnit str2durationUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Illegal duration unit '" + unit + "'");
        }
        switch (unit.toUpperCase(Locale.ROOT)) {
            case "NANO":
            case "NANOS":
                return ChronoUnit.NANOS;
            case "MICRO":
            case "MICROS":
                return ChronoUnit.MICROS;
            case "MILLI":
            case "MILLIS":
                return ChronoUnit.MILLIS;
            case "SECOND":
            case "SECONDS":
                return ChronoUnit.SECONDS;
            case "MINUTE":
            case "MINUTES":
                return ChronoUnit.MINUTES;
            case "HOUR":
            case "HOURS":
                return ChronoUnit.HOURS;
            case "DAY":
            case "DAYS":
                return ChronoUnit.DAYS;
            default:
                throw new IllegalArgumentException("Illegal duration unit '" + unit + "'");
        }
    }

    private Duration generateDuration(long value, ChronoUnit unit) {
        return Duration.of(value, unit);
    }

    private String toString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}

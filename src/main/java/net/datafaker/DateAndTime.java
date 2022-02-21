package net.datafaker;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A generator of random dates.
 *
 * @author pmiklos
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
    public Date future(int atMost, TimeUnit unit) {
        Date now = new Date();
        Date aBitLaterThanNow = new Date(now.getTime() + 1000);
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
        return toString(future(atMost, unit), pattern, faker.getLocale());
    }

    /**
     * Generates a future date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @return a future date from now, with a minimum time.
     */
    public Date future(int atMost, int minimum, TimeUnit unit) {
        Date now = new Date();
        Date minimumDate = new Date(now.getTime() + unit.toMillis(minimum));
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
        return toString(future(atMost, minimum, unit), pattern, faker.getLocale());
    }

    /**
     * Generates a future date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time ahead to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the future date relative to this date.
     * @return a future date relative to {@code referenceDate}.
     */
    public Date future(int atMost, TimeUnit unit, Date referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis += 1 + faker.random().nextLong(upperBound - 1);

        return new Date(futureMillis);
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
        return toString(future(atMost, unit, referenceDate), pattern, faker.getLocale());
    }

    /**
     * Generates a past date from now. Note that there is a 1 second slack added.
     *
     * @param atMost at most this amount of time earlier from now exclusive.
     * @param unit   the time unit.
     * @return a past date from now.
     */
    public Date past(int atMost, TimeUnit unit) {
        Date now = new Date();
        Date aBitEarlierThanNow = new Date(now.getTime() - 1000);
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
        return toString(past(atMost, unit), pattern, faker.getLocale());
    }

    /**
     * Generates a past date from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @return a past date from now.
     */
    public Date past(int atMost, int minimum, TimeUnit unit) {
        Date now = new Date();
        Date minimumDate = new Date(now.getTime() - unit.toMillis(minimum));
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
        return toString(past(atMost, minimum, unit), pattern, faker.getLocale());
    }

    /**
     * Generates a past date relative to the {@code referenceDate}.
     *
     * @param atMost        at most this amount of time past to the {@code referenceDate} exclusive.
     * @param unit          the time unit.
     * @param referenceDate the past date relative to this date.
     * @return a past date relative to {@code referenceDate}.
     */
    public Date past(int atMost, TimeUnit unit, Date referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis -= 1 + faker.random().nextLong(upperBound - 1);

        return new Date(futureMillis);
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
        return toString(past(atMost, unit, referenceDate), pattern, faker.getLocale());
    }

    /**
     * Generates a random date between two dates.
     *
     * @param from the lower bound inclusive
     * @param to   the upper bound exclusive
     * @return a random date between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} date represents an earlier date than {@code from} date.
     */
    public Date between(Date from, Date to) throws IllegalArgumentException {
        if (to.before(from)) {
            throw new IllegalArgumentException("Invalid date range, the upper bound date is before the lower bound.");
        }

        if (from.equals(to)) {
            System.out.println("\t" + to.getTime() + " vs " + from.getTime());
            return from;
        }

        long offsetMillis = faker.random().nextLong(to.getTime() - from.getTime());
        return new Date(from.getTime() + offsetMillis);
    }


    public LocalDate between(LocalDate from, LocalDate to) throws IllegalArgumentException {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Invalid date range, the upper bound date is before the lower bound.");
        }

        if (from.equals(to)) {
            return from;
        }

        return LocalDate.ofEpochDay(
            faker.random().nextLong(to.getLong(ChronoField.EPOCH_DAY), from.getLong(ChronoField.EPOCH_DAY) + 1));
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
    public String between(Date from, Date to, String pattern) throws IllegalArgumentException {
        return toString(between(from, to), pattern, faker.getLocale());
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
        return toString(birthday(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE), pattern, faker.getLocale());
    }

    /**
     * Generates a random birthday between two ages from now.
     *
     * @param minAge the minimal age
     * @param maxAge the maximal age
     * @return a random birthday between {@code minAge} and {@code maxAge} years ago from now.
     * @throws IllegalArgumentException if the {@code maxAge} is lower than {@code minAge}.
     */
    public LocalDate birthday(int minAge, int maxAge) {
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        return between(LocalDate.of(year, month, day).minus(maxAge, ChronoUnit.YEARS),
            LocalDate.of(year, month, day).minus(minAge, ChronoUnit.YEARS));
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
        return toString(birthday(minAge, maxAge), pattern, faker.getLocale());
    }

    private String toString(Date date, String pattern, Locale locale) {
        return new SimpleDateFormat(pattern, locale).format(date);
    }

    private String toString(LocalDate localDate, String pattern, Locale locale) {
        return DateTimeFormatter.ofPattern(pattern, locale).format(localDate);
    }
}

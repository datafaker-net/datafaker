package net.datafaker.providers.base;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @since 1.4.0
 */
public class Time extends AbstractProvider<BaseProviders> {

    protected Time(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a future time from now. Note that there is a 1 second slack to avoid generating a past time.
     *
     * @param atMost at most this amount of time ahead from now exclusive.
     * @param unit   the time unit.
     * @return a future time from now.
     */
    public long future(int atMost, ChronoUnit unit) {
        final LocalTime now = LocalTime.now();
        final long atMostTime = now.plus(atMost, unit).toNanoOfDay();
        final long time = now.toNanoOfDay();
        return faker.number().numberBetween(time, atMostTime);
    }

    /**
     * Generates and converts to string representation a future time from now.
     * Note that there is a 1 second slack to avoid generating a past time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param unit    the time unit.
     * @param pattern time pattern to convert to string.
     * @return a string representation of a future time from now.
     */
    public String future(int atMost, ChronoUnit unit, String pattern) {
        return LocalTime.ofNanoOfDay(future(atMost, unit)).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Generates a future time from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @return a future time from now, with a minimum time.
     */
    public long future(int atMost, int minimum, ChronoUnit unit) {
        final LocalTime now = LocalTime.now();
        final long atMostTime = now.plus(atMost, unit).toNanoOfDay();
        final long time = now.plus(minimum, unit).toNanoOfDay();
        return faker.number().numberBetween(time, atMostTime);
    }

    /**
     * Generates and converts to string representation
     * of a future time from now, with a minimum time.
     *
     * @param atMost  at most this amount of time ahead from now exclusive.
     * @param minimum the minimum amount of time in the future from now.
     * @param unit    the time unit.
     * @param pattern time pattern to convert to string.
     * @return a string representation of a future time from now, with a minimum time.
     */
    public String future(int atMost, int minimum, ChronoUnit unit, String pattern) {
        return LocalTime.ofNanoOfDay(future(atMost, minimum, unit)).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Generates a past time from now. Note that there is a 1 second slack added.
     *
     * @param atLeast at most this amount of time earlier from now exclusive.
     * @param unit    the time unit.
     * @return a past time from now.
     */
    public long past(int atLeast, ChronoUnit unit) {
        final LocalTime now = LocalTime.now();
        final long atLeastTime = now.minus(atLeast, unit).toNanoOfDay();
        final long time = now.toNanoOfDay();
        return faker.number().numberBetween(atLeastTime, time);
    }

    /**
     * Generates a string representation of a past time from now.
     * Note that there is a 1 second slack added.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param unit    the time unit.
     * @param pattern time pattern to convert to string.
     * @return a string representation of a past time from now.
     */
    public String past(int atMost, ChronoUnit unit, String pattern) {
        return LocalTime.ofNanoOfDay(past(atMost, unit)).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Generates a past time from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @return a past time from now.
     */
    public long past(int atMost, int minimum, ChronoUnit unit) {
        final LocalTime now = LocalTime.now();
        final long atMostTime = now.minus(atMost, unit).toNanoOfDay();
        final long time = now.minus(minimum, unit).toNanoOfDay();
        return faker.number().numberBetween(atMostTime, time);
    }

    /**
     * Generates and converts to string representation a past time from now, with a minimum time.
     *
     * @param atMost  at most this amount of time earlier from now exclusive.
     * @param minimum the minimum amount of time in the past from now.
     * @param unit    the time unit.
     * @param pattern time pattern to convert to string.
     * @return a string representation of a past time from now, with a minimum time.
     */
    public String past(int atMost, int minimum, ChronoUnit unit, String pattern) {
        return LocalTime.ofNanoOfDay(past(atMost, minimum, unit)).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Generates a random time between two times.
     *
     * @param from the lower bound inclusive
     * @param to   the upper bound exclusive
     * @return a random time between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} time represents an earlier time than {@code from} time.
     */
    public long between(LocalTime from, LocalTime to) throws IllegalArgumentException {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Invalid time range, the upper bound time is before the lower bound.");
        }

        if (from.equals(to)) {
            return from.toNanoOfDay();
        }

        return faker.number().numberBetween(from.toNanoOfDay(), to.toNanoOfDay());
    }

    /**
     * Generates a string representation of a random time between two times.
     *
     * @param from    the lower bound inclusive
     * @param to      the upper bound exclusive
     * @param pattern time pattern to convert to string.
     * @return a string representation of a random time between {@code from} and {@code to}.
     * @throws IllegalArgumentException if the {@code to} time represents an earlier time than {@code from} time.
     */
    public String between(LocalTime from, LocalTime to, String pattern) throws IllegalArgumentException {
        return LocalTime.ofNanoOfDay(between(from, to)).format(DateTimeFormatter.ofPattern(pattern));
    }
}

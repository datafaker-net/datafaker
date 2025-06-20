package net.datafaker.service;

import java.util.function.Function;

public record Range<T extends Comparable<T>>(Bound<T> from, Bound<T> to) {
    public enum End {INCLUSIVE, EXCLUSIVE}
    public record Bound<T>(T value, End end) {}

    /**
     * A range that contains all values
     * 1. greater than or equal to {@code from}, and
     * 2. less than or equal to {@code to}.
     */
    public static <T extends Comparable<T>> Range<T> inclusive(T from, T to) {
        if (from.compareTo(to) > 0) {
            throw new IllegalArgumentException("Lower bound (%s) > upper bound (%s)".formatted(from, to));
        }
        return new Range<>(new Bound<>(from, End.INCLUSIVE), new Bound<>(to, End.INCLUSIVE));
    }

    /**
     * A range that contains all values
     * 1. greater than or equal to {@code from}, and
     * 2. less than {@code to}.
     */
    public static <T extends Comparable<T>> Range<T> inclusiveExclusive(T from, T to) {
        if (from.compareTo(to) >= 0) {
            throw new IllegalArgumentException("Lower bound (%s) >= upper bound (%s)".formatted(from, to));
        }
        return new Range<>(new Bound<>(from, End.INCLUSIVE), new Bound<>(to, End.EXCLUSIVE));
    }

    /**
     * A range that contains all values
     * 1. strictly greater than {@code from}, and
     * 2. strictly less than {@code to}.
     */
    public static <T extends Number & Comparable<T>> Range<T> exclusive(T from, T to) {
        if (to.longValue() == Long.MIN_VALUE) {
            throw new IllegalArgumentException("Lower bound (%s) >= upper bound (%s)".formatted(from, to));
        }
        long upperLimit = to.longValue() - 1;
        if (from.longValue() >= upperLimit) {
            throw new IllegalArgumentException("Lower bound (%s) >= upper bound-1 (%s)".formatted(from, upperLimit));
        }
        return new Range<>(new Bound<>(from, End.EXCLUSIVE), new Bound<>(to, End.EXCLUSIVE));
    }

    /**
     * A range that contains all values
     * 1. strictly greater than {@code from}, and
     * 2. less than or equal to {@code to}.
     */
    public static <T extends Comparable<T>> Range<T> exclusiveInclusive(T from, T to) {
        if (from.compareTo(to) >= 0) {
            throw new IllegalArgumentException("Lower bound (%s) >= upper bound (%s)".formatted(from, to));
        }
        return new Range<>(new Bound<>(from, End.EXCLUSIVE), new Bound<>(to, End.INCLUSIVE));
    }

    public <V extends Comparable<V>> Range<V> cast(Function<T, V> caster) {
        return new Range<>(
            new Bound<>(caster.apply(from.value), from.end),
            new Bound<>(caster.apply(to.value), to.end)
        );
    }
}

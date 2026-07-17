package net.datafaker.providers.base;

import java.util.List;
import java.util.Set;

/**
 * @since 0.8.0
 * @deprecated Use {@link Selection} instead
 */
@Deprecated(since = "3.0.0")
public class Options extends AbstractProvider<BaseProviders> {

    protected Options(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns a random element from a varargs.
     *
     * @param options The varargs to take a random element from.
     * @param <E>     The type of the elements in the varargs.
     * @return A randomly selected element from the varargs.
     */
    @SafeVarargs
    public final <E> E option(E... options) {
        return faker.selection().oneOf(options);
    }

    public final char option(char[] options) {
        return faker.selection().oneOf(options);
    }

    public final int option(int[] options) {
        return faker.selection().oneOf(options);
    }

    public final long option(long[] options) {
        return faker.selection().oneOf(options);
    }

    public final float option(float[] options) {
        return faker.selection().oneOf(options);
    }

    public final double option(double[] options) {
        return faker.selection().oneOf(options);
    }

    public final short option(short[] options) {
        return faker.selection().oneOf(options);
    }

    public final boolean option(boolean[] options) {
        return faker.selection().oneOf(options);
    }

    public final byte option(byte[] options) {
        return faker.selection().oneOf(options);
    }

    /**
     * Returns a random unique subset of elements from a varargs.
     *
     * @param size    The size of subset to return.
     * @param options The varargs to take a random element from.
     * @param <E>     The type of the elements in the varargs.
     * @return A randomly selected unique subset from the varargs.
     * If size is negative then {@code IllegalArgumentException} will be thrown.
     * If size is zero then an empty subset will be returned.
     * If size is larger than a unique set from options then all options will be returned.
     */
    @SafeVarargs
    public final <E> Set<E> subset(int size, E... options) {
        return faker.selection().manyOf(size, options);
    }

    /**
     * Returns a random String element from a varargs.
     *
     * @param options The varargs to take a random element from.
     * @return A randomly selected element from the varargs.
     */
    public String option(String... options) {
        return faker.selection().oneOf(options);
    }

    /**
     * Returns a random unique subset of elements from a varargs.
     *
     * @param size    The size of subset to return.
     * @param options The varargs to take a random element from.
     * @return A randomly selected unique subset from the varargs.
     * If size is negative then {@code IllegalArgumentException} will be thrown.
     * If size is zero then an empty subset will be returned.
     * If size is larger than a unique set from options then all options will be returned.
     */
    public final Set<String> subset(int size, String... options) {
        return faker.selection().manyOf(size, options);
    }

    /**
     * Returns a random element from Enum.
     *
     * @param enumeration The Enum to take a random element from.
     * @return A randomly selected element from the enum.
     */
    public <E extends Enum<E>> E option(Class<E> enumeration) {
        return faker.selection().oneOf(enumeration);
    }

    /**
     * Returns a random element from an array.
     *
     * @param array The array to take a random element from.
     * @param <E>   The type of the elements in the array.
     * @return A randomly selected element from the array.
     */
    public <E> E nextElement(E[] array) {
        return faker.selection().oneOf(array);
    }

    /**
     * Returns a random element from a list.
     *
     * @param list The list to take a random element from.
     * @param <E>  The type of the elements in the list.
     * @return A randomly selected element from the list.
     */
    public <E> E nextElement(List<E> list) {
        return faker.selection().oneOf(list);
    }
}

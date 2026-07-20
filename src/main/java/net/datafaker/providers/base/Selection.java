package net.datafaker.providers.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides utility methods for selecting an element from an array, collection or enum.
 *
 * @since 3.0.0
 */
public class Selection extends AbstractProvider<BaseProviders> {

    protected Selection(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns a random element from an array or varargs.
     *
     * @param options The varargs to take a random element from.
     * @param <E>     The type of the elements in the array.
     * @return A randomly selected element from the array.
     */
    @SafeVarargs
    public final <E> E oneOf(E... options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final char oneOf(char[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final int oneOf(int[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final long oneOf(long[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final float oneOf(float[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final double oneOf(double[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final short oneOf(short[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final boolean oneOf(boolean[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final byte oneOf(byte[] options) {
        return options[faker.random().nextInt(options.length)];
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
    public final <E> Set<E> setOf(int size, E... options) {
        if (size < 0) {
            throw new IllegalArgumentException("size should be not negative: " + size);
        }
        if (size == 0) {
            return Set.of();
        }
        List<E> opts = Stream.of(options).distinct().collect(Collectors.toList());
        if (size >= opts.size()) {
            return new HashSet<>(opts);
        }
        int i = 0;
        Set<E> set = new HashSet<>();
        while (i < size) {
            int randomIndex = faker.random().nextInt(opts.size());
            set.add(opts.remove(randomIndex));
            i++;
        }

        return set;
    }

    /**
     * Returns a random element from an enum.
     *
     * @param enumeration The enum to take a random element from.
     * @return A randomly selected element from the enum.
     */
    public <E extends Enum<E>> E oneOf(Class<E> enumeration) {
        return oneOf(enumeration.getEnumConstants());
    }

    /**
     * Returns a random element from a list.
     *
     * @param list The list to take a random element from.
     * @param <E>  The type of the elements in the list.
     * @return A randomly selected element from the list.
     */
    public <E> E oneOf(List<E> list) {
        return list.get(faker.random().nextInt(list.size()));
    }

    /**
     * Returns a random element from a collection.
     *
     * @param collection The collection to take a random element from.
     * @param <E>        The type of the elements in the collection.
     * @return A randomly selected element from the collection.
     * @throws IllegalArgumentException if the collection is empty.
     * @since 3.0.0
     */
    public <E> E oneOf(Collection<E> collection) {
        return collection.stream()
            .skip(faker.random().nextInt(collection.size()))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Collection is empty"));
    }
}

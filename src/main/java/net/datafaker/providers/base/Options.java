package net.datafaker.providers.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 0.8.0
 */
public class Options extends AbstractProvider<BaseProviders> {

    protected Options(BaseProviders faker) {
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
    public final <E> E option(E... options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final char option(char[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final int option(int[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final long option(long[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final float option(float[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final double option(double[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final short option(short[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final boolean option(boolean[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    public final byte option(byte[] options) {
        return options[faker.random().nextInt(options.length)];
    }

    /**
     * Returns a random unique subset of elements from an varargs.
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
            set.add(opts.get(randomIndex));
            opts.remove(randomIndex);
            i++;
        }

        return set;
    }

    /**
     * Returns a random String element from an varargs.
     *
     * @param options The varargs to take a random element from.
     * @return A randomly selected element from the varargs.
     */
    public String option(String... options) {
        return options[faker.random().nextInt(options.length)];
    }

    /**
     * Returns a random unique subset of elements from an varargs.
     *
     * @param size    The size of subset to return.
     * @param options The varargs to take a random element from.
     * @return A randomly selected unique subset from the varargs.
     * If size is negative then {@code IllegalArgumentException} will be thrown.
     * If size is zero then an empty subset will be returned.
     * If size is larger than a unique set from options then all options will be returned.
     */
    public final Set<String> subset(int size, String... options) {
        if (size < 0) {
            throw new IllegalArgumentException("size should be not negative: " + size);
        }
        if (size == 0) {
            return Set.of();
        }
        List<String> opts = Stream.of(options).distinct().collect(Collectors.toList());
        if (size >= opts.size()) {
            return new HashSet<>(opts);
        }
        int i = 0;
        Set<String> set = new HashSet<>();
        while (i < size) {
            int randomIndex = faker.random().nextInt(opts.size());
            set.add(opts.get(randomIndex));
            opts.remove(randomIndex);
            i++;
        }

        return set;
    }

    /**
     * Returns a random element from Enum.
     *
     * @param enumeration The Enum to take a random element from.
     * @return A randomly selected element from the enum.
     */
    public <E extends Enum<E>> E option(Class<E> enumeration) {
        return faker.random().nextEnum(enumeration);
    }

    /**
     * Returns a random element from an array.
     *
     * @param array The array to take a random element from.
     * @param <E>   The type of the elements in the array.
     * @return A randomly selected element from the array.
     * @deprecated Use {@link #option(Object[]) option(E...)}
     */
    @Deprecated(since = "3.0.0", forRemoval = true)
    public <E> E nextElement(E[] array) {
        return option(array);
    }

    /**
     * Returns a random element from a list.
     *
     * @param list The list to take a random element from.
     * @param <E>  The type of the elements in the list.
     * @return A randomly selected element from the list.
     * @deprecated Use {@link #option(List)}
     */
    @Deprecated(since = "3.0.0", forRemoval = true)
    public <E> E nextElement(List<E> list) {
        return option(list);
    }

    /**
     * Returns a random element from a list.
     *
     * @param list The list to take a random element from.
     * @param <E>  The type of the elements in the list.
     * @return A randomly selected element from the list.
     */
    public <E> E option(List<E> list) {
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
    public <E> E option(Collection<E> collection) {
        return collection.stream()
            .skip(faker.random().nextInt(collection.size()))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Collection is empty"));
    }
}

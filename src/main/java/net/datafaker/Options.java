package net.datafaker;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 0.8.0
 */
public class Options {
    private final Faker faker;

    protected Options(Faker faker) {
        this.faker = faker;
    }

    /**
     * Returns a random element from an varargs.
     *
     * @param options The varargs to take a random element from.
     * @param <E>     The type of the elements in the varargs.
     * @return A randomly selected element from the varargs.
     */
    @SafeVarargs
    public final <E> E option(E... options) {
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
    public final <E> Set<E> subset(int size, E... options) {
        if (size < 0) {
            throw new IllegalArgumentException("size should be not negative");
        }
        if (size == 0) {
            return Collections.emptySet();
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
            throw new IllegalArgumentException("size should be not negative");
        }
        if (size == 0) {
            return Collections.emptySet();
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
        E[] enumConstants = enumeration.getEnumConstants();
        return enumConstants[faker.random().nextInt(enumConstants.length)];
    }

    /**
     * Returns a random element from an array.
     *
     * @param array The array to take a random element from.
     * @param <E>   The type of the elements in the array.
     * @return A randomly selected element from the array.
     */
    public <E> E nextElement(E[] array) {
        return array[faker.random().nextInt(array.length)];
    }

    /**
     * Returns a random element from a list.
     *
     * @param list The list to take a random element from.
     * @param <E>  The type of the elements in the list.
     * @return A randomly selected element from the list.
     */
    public <E> E nextElement(List<E> list) {
        return list.get(faker.random().nextInt(list.size()));
    }
}

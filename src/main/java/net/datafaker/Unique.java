package net.datafaker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class contains methods that ensure uniqueness across separate invocations. This can be helpful for unit tests
 * and automated tests where certain values need to be different.
 */
public class Unique extends AbstractProvider {

    private final Map<Locale, Map<String, List<String>>> valuesByKeyAndLocale = new HashMap<>();

    public Unique(Faker faker) {
        super(faker);
    }

    /**
     * Returns a random value from the YAML files that is guaranteed to be unique for the given key and locale set on
     * the current faker instance.
     *
     * Since unique values are tracked by both the key and the locale, manually changing the locale could cause
     * this method to return the same value for a given key if there is not a separate locale for the given key.
     *
     * @param key the key of the values to fetch from the YAML files
     * @throws NoSuchElementException if {@code key} does not correspond to a list of valid data types in the YAML
     *                                files, or if all possible values found for {@code key} and current locale
     *                                have been returned
     * @return a unique random value based on {@code key} and the current locale
     */
    public String fetchFromYaml(String key) {
        Locale locale = faker.getLocale();

        Map<String, List<String>> valuesByKey = valuesByKeyAndLocale.getOrDefault(locale, new HashMap<>());
        List<String> values = valuesByKey.get(key);

        if (values == null) {
            values = fetchValues(key);
        }

        if (values.isEmpty()) {
            throw new NoSuchElementException(String.format(
                "All possible values have been generated for key %s under locale %s",
                key,
                locale
            ));
        }

        int index = faker.random().nextInt(0, values.size() - 1);
        String value = removeAtIndex(values, index);

        valuesByKey.put(key, values);
        valuesByKeyAndLocale.put(locale, valuesByKey);

        return value;
    }

    private String removeAtIndex(List<String> values, int index) {
        int lastIndex = values.size() - 1;
        Collections.swap(values, index, lastIndex);
        return values.remove(lastIndex);
    }

    private List<String> fetchValues(String key) {
        Object object = faker.fakeValuesService().fetchObject(key);

        if (!(object instanceof List)) {
            throw noValuesFoundException(key);
        }

        List<String> values = ((List<?>) object).stream()
            .filter(value -> !(value instanceof List))
            .map(String::valueOf)
            .collect(Collectors.toList());

        if (values.isEmpty()) {
            throw noValuesFoundException(key);
        }

        return values;
    }

    private NoSuchElementException noValuesFoundException(String key) {
        return new NoSuchElementException(String.format(
            "No values found for key %s",
            key
        ));
    }
}

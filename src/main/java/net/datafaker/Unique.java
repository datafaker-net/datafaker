package net.datafaker;

import java.util.*;

public class Unique extends AbstractProvider {

    private final Map<Locale, Map<String, List<String>>> valuesByKeyAndLocale = new HashMap<>();

    public Unique(Faker faker) {
        super(faker);
    }

    public String fetchFromYaml(String key) {
        Locale locale = faker.getLocale();

        Map<String, List<String>> valuesByKey = valuesByKeyAndLocale.getOrDefault(locale, new HashMap<>());
        List<String> values = valuesByKey.get(key);

        if (values == null) {
            values = fetchValues(key);
        }

        if (values.isEmpty()) {
            throw new RuntimeException(String.format(
                "All possible values have been generated for key %s under locale %s",
                key,
                locale
            ));
        }

        int index = faker.random().nextInt(0, values.size() - 1);
        String value = values.remove(index);

        valuesByKey.put(key, values);
        valuesByKeyAndLocale.put(locale, valuesByKey);

        return value;
    }

    @SuppressWarnings("unchecked")
    private List<String> fetchValues(String key) {
        Object object = faker.fakeValuesService().fetchObject(key);

        if (object == null) {
            throw new RuntimeException(String.format(
                "No values found for key %s",
                key
            ));
        }

        if (!(object instanceof List)) {
            throw new RuntimeException(String.format(
                "Object found for key %s is not in list format",
                key
            ));
        }

        return new ArrayList<>((List<String>) object);
    }
}

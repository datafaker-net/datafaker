package net.datafaker.providers.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.6.0
 * @deprecated Replaced by Text
 */
@Deprecated
public class Password extends AbstractProvider<BaseProviders> {

    public static final String EN_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String EN_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String DEFAULT_SPECIAL = "!@#$%^&*";

    public Password(BaseProviders faker) {
        super(faker);
    }

    public static class PasswordRuleConfig {
        private final int fixedNumberOfCharacters;
        private final Map<String, Integer> map;

        private final int numberOfRequiredSymbols;

        private PasswordRuleConfig(int fixedNumberOfCharacters, Map<String, Integer> map, int numberOfRequiredSymbols) {
            this.fixedNumberOfCharacters = fixedNumberOfCharacters;
            this.map = Collections.unmodifiableMap(map);
            this.numberOfRequiredSymbols = numberOfRequiredSymbols;
        }

        public int getFixedNumberOfCharacters() {
            return fixedNumberOfCharacters;
        }

        public Map<String, Integer> getMap() {
            return map;
        }

        public int getNumberOfRequiredSymbols() {
            return numberOfRequiredSymbols;
        }

    }

    public static class PasswordSymbolsBuilder {
        private int minLength;
        private int maxLength;
        private final Map<String, Integer> map = new HashMap<>();
        private boolean throwIfLengthSmall = false;

        private PasswordSymbolsBuilder() {
        }

        public static PasswordSymbolsBuilder builder() {
            return new PasswordSymbolsBuilder();
        }

        public PasswordSymbolsBuilder with(String listOfSymbols, int times) {
            if (times < 0) {
                throw new IllegalArgumentException("times should be non-negative");
            }
            map.put(listOfSymbols, times);
            return this;
        }

        public PasswordSymbolsBuilder with(String listOfSymbols) {
            return with(listOfSymbols, 0);
        }

        public PasswordSymbolsBuilder withMinLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public PasswordSymbolsBuilder withMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public PasswordSymbolsBuilder throwIfLengthSmall(boolean throwIfLengthSmall) {
            this.throwIfLengthSmall = throwIfLengthSmall;
            return this;
        }

        public PasswordRuleConfig build(BaseProviders faker) {
            if (maxLength < minLength) {
                throw new IllegalArgumentException("Max length should be not smaller than min length");
            }
            int minSize = map.values().stream().filter(t -> t > 0).reduce(0, Integer::sum);
            if (minSize > minLength && throwIfLengthSmall) {
                throw new IllegalArgumentException("Min length should be not smaller than number of required characters");
            }
            int fixedNumberOfCharacters = faker.number().numberBetween(minLength, maxLength + 1);
            return new PasswordRuleConfig(fixedNumberOfCharacters, map, minSize);
        }
    }

    public String password(PasswordRuleConfig passwordRuleConfig) {
        final int fixedNumberOfCharacters = passwordRuleConfig.getFixedNumberOfCharacters();
        final int numberOfRequiredSymbols = passwordRuleConfig.getNumberOfRequiredSymbols();
        if (fixedNumberOfCharacters < numberOfRequiredSymbols) {
            return "";
        }
        final Map<String, Integer> map = new HashMap<>(passwordRuleConfig.getMap());
        char[] buffer = new char[fixedNumberOfCharacters];
        int idx = 0;
        String[] keys = map.keySet().toArray(new String[0]);
        int numberOfRequired = 0;
        while (idx < buffer.length) {
            if (numberOfRequiredSymbols > numberOfRequired
                && numberOfRequiredSymbols - numberOfRequired == buffer.length - idx) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        buffer[idx++] = entry.getKey().charAt(faker.random().nextInt(entry.getKey().length()));
                        numberOfRequired++;
                    }
                    map.put(entry.getKey(), 0);
                    if (idx == buffer.length) break;
                }
            } else {
                String key = faker.options().option(keys);
                Integer curValue = map.get(key);
                if (curValue > 0) {
                    map.put(key, curValue - 1);
                    numberOfRequired++;
                }
                buffer[idx++] = key.charAt(faker.random().nextInt(key.length()));
            }
        }
        return String.valueOf(buffer);
    }
}

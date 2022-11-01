package net.datafaker.providers.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates random text in a flexible way.
 *
 * @since 1.7.0
 */
public class Text extends AbstractProvider<BaseProviders> {

    public Character character() {
        return text(1, 1, true).charAt(0);
    }

    public Character uppercaseCharacter() {
        return text(1, 1).toUpperCase(faker.getContext().getLocale()).charAt(0);
    }

    public Character lowercaseCharacter() {
        return text(1, 1, false).charAt(0);
    }

    /**
     * @return A lowercase string of 20 to 80 characters long.
     */
    public String text() {
        return text(false);
    }

    /**
     * @param includeDigit if digits should be included
     * @return A lowercase string of 20 to 80 characters long.
     */
    public String text(boolean includeDigit) {
        return text(20, 80, false, false, includeDigit);
    }

    /**
     * @param length The length of the string to return
     * @return A lowercase string of exact length
     */
    public String text(int length) {
        return text(length, length, false);
    }

    /**
     * @param minimumLength The minimum length (inclusive)
     * @param maximumLength The maximum length (inclusive)
     * @return A lowercase string between minimum and maximum length (inclusive)
     */
    public String text(int minimumLength, int maximumLength) {
        return text(minimumLength, maximumLength, false);
    }

    public String text(int minimumLength, int maximumLength, boolean includeUppercase) {
        return text(minimumLength, maximumLength, includeUppercase, false);
    }

    public String text(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial) {
        return text(minimumLength, maximumLength, includeUppercase, includeSpecial, false);
    }

    public String text(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        TextSymbolsBuilder builder =
            TextSymbolsBuilder.builder()
                .with(Text.EN_LOWERCASE);
        if (includeUppercase) builder = builder.with(Text.EN_UPPERCASE, 1);
        if (includeSpecial) builder = builder.with(Text.DEFAULT_SPECIAL, 1);
        if (includeDigit) builder = builder.with(Text.DIGITS, 1);

        Text.TextRuleConfig config = builder.withMinLength(minimumLength)
            .withMaxLength(maximumLength)
            .build(faker);

        return faker.text().text(config);
    }

    public static final String EN_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String EN_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String DEFAULT_SPECIAL = "!@#$%^&*";

    public Text(BaseProviders faker) {
        super(faker);
    }

    public static class TextRuleConfig {
        private final int fixedNumberOfCharacters;
        private final Map<String, Integer> map;

        private final int numberOfRequiredSymbols;

        private TextRuleConfig(int fixedNumberOfCharacters, Map<String, Integer> map, int numberOfRequiredSymbols) {
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

    public static class TextSymbolsBuilder {
        private int minLength;
        private int maxLength;
        private final Map<String, Integer> map = new HashMap<>();
        private boolean throwIfLengthSmall = false;

        private TextSymbolsBuilder() {
        }

        public static TextSymbolsBuilder builder() {
            return new TextSymbolsBuilder();
        }

        public TextSymbolsBuilder with(String listOfSymbols, int times) {
            if (times < 0) {
                throw new IllegalArgumentException("times should be non-negative");
            }
            map.put(listOfSymbols, times);
            return this;
        }

        public TextSymbolsBuilder with(String listOfSymbols) {
            return with(listOfSymbols, 0);
        }

        public TextSymbolsBuilder withMinLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public TextSymbolsBuilder withMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public TextSymbolsBuilder throwIfLengthSmall(boolean throwIfLengthSmall) {
            this.throwIfLengthSmall = throwIfLengthSmall;
            return this;
        }

        public TextRuleConfig build(BaseProviders faker) {
            if (maxLength < minLength) {
                throw new IllegalArgumentException("Max length should be not smaller than min length");
            }
            int minSize = map.values().stream().filter(t -> t > 0).reduce(0, Integer::sum);
            if (minSize > minLength && throwIfLengthSmall) {
                throw new IllegalArgumentException("Min length should be not smaller than number of required characters");
            }
            int fixedNumberOfCharacters = faker.number().numberBetween(minLength, maxLength + 1);
            return new TextRuleConfig(fixedNumberOfCharacters, map, minSize);
        }
    }

    public String text(TextRuleConfig textRuleConfig) {
        final int fixedNumberOfCharacters = textRuleConfig.getFixedNumberOfCharacters();
        final int numberOfRequiredSymbols = textRuleConfig.getNumberOfRequiredSymbols();
        if (fixedNumberOfCharacters < numberOfRequiredSymbols) {
            return "";
        }
        final Map<String, Integer> map = new HashMap<>(textRuleConfig.getMap());
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

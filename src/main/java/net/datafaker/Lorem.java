package net.datafaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.datafaker.internal.helper.WordUtils.capitalize;

/**
 * @since 0.8.0
 */
public class Lorem {
    public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String DEFAULT_SPECIAL = "!@#$%^&*";

    public static class PasswordSymbolsBuilder {
        private final Map<String, Integer> map = new HashMap<>();

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

        public Map<String, Integer> build() {
            return Collections.unmodifiableMap(map);
        }
    }

    public static final Map<String, Integer> LOWER_UPPER_DIGIT_SPECIAL_AT_LEAST_ONES =
        PasswordSymbolsBuilder.builder().with(LOWERCASE, 1)
            .with(UPPERCASE, 1)
            .with(DIGITS, 1)
            .with(DEFAULT_SPECIAL, 1).build();
    private final Faker faker;

    protected Lorem(Faker faker) {
        this.faker = faker;
    }

    public char character() {
        return character(false);
    }

    public char character(boolean includeUppercase) {
        return characters(1, includeUppercase).charAt(0);
    }

    public String characters() {
        return characters(255, false);
    }

    public String characters(boolean includeUppercase) {
        return characters(255, includeUppercase);
    }

    public String characters(int minimumLength, int maximumLength) {
        return characters(faker.random().nextInt(minimumLength, maximumLength), false);
    }

    @Deprecated // use net.datafaker.Lorem.characters(int, java.util.Map<java.lang.String,java.lang.Integer>, boolean)
    public String characters(int minimumLength, int maximumLength, boolean includeUppercase) {
        return characters(minimumLength, maximumLength, includeUppercase, false);
    }

    @Deprecated // net.datafaker.Lorem.characters(int, java.util.Map<java.lang.String,java.lang.Integer>, boolean)
    public String characters(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeDigit) {
        return characters(minimumLength, maximumLength, includeUppercase, false, includeDigit);
    }

    public String characters(int fixedNumberOfCharacters) {
        return characters(fixedNumberOfCharacters, false);
    }

    @Deprecated // use net.datafaker.Lorem.characters(int, java.util.Map<java.lang.String,java.lang.Integer>, boolean)
    public String characters(int fixedNumberOfCharacters, boolean includeUppercase) {
        PasswordSymbolsBuilder passwordSymbolsBuilder =
            PasswordSymbolsBuilder.builder().with(LOWERCASE, 0)
                .with(DIGITS, 0); // 0 to keep old behavior
        if (includeUppercase) {
            passwordSymbolsBuilder.with(UPPERCASE, 1);
        }
        return characters(fixedNumberOfCharacters, passwordSymbolsBuilder.build(), false);
    }

    @Deprecated // use net.datafaker.Lorem.characters(int, java.util.Map<java.lang.String,java.lang.Integer>, boolean)
    public String characters(int minimumLength, int maximumLength,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return characters(faker.random().nextInt(minimumLength, maximumLength), includeUppercase, includeSpecial, includeDigit);
    }

    public String characters(int minimumLength, int maximumLength, Map<String, Integer> listOfSymbolSets, boolean throwIfLengthSmall) {
        return characters(faker.random().nextInt(minimumLength, maximumLength), listOfSymbolSets, throwIfLengthSmall);
    }

    public String characters(int fixedNumberOfCharacters, Map<String, Integer> listOfSymbolSets, boolean throwIfLengthSmall) {
        final Map<String, Integer> requiredOnly = listOfSymbolSets.entrySet().stream()
            .filter(t -> t.getValue() > 0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        final int minSize = requiredOnly.values().stream().reduce(0, Integer::sum);
        if (fixedNumberOfCharacters < minSize) {
            if (throwIfLengthSmall) {
                throw new IllegalArgumentException("The length should be not smaller than number of required symbols");
            } else {
                return "";
            }
        }
        char[] buffer = new char[fixedNumberOfCharacters];
        int idx = 0;
        for (Map.Entry<String, Integer> entry : requiredOnly.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                buffer[idx++] = entry.getKey().charAt(faker.random().nextInt(entry.getKey().length()));
            }
        }
        final List<String> keys = new ArrayList<>(listOfSymbolSets.keySet());
        while (idx < fixedNumberOfCharacters) {
            final int index = faker.random().nextInt(keys.size());
            buffer[idx++] = keys.get(index).charAt(faker.random().nextInt(keys.get(index).length()));
        }
        shuffle(buffer);
        return String.valueOf(buffer);
    }

    public String characters(int fixedNumberOfCharacters, boolean includeUppercase, boolean includeDigit) {
        return characters(fixedNumberOfCharacters, includeUppercase, false, includeDigit);
    }

    public String characters(int fixedNumberOfCharacters,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {

        PasswordSymbolsBuilder builder = PasswordSymbolsBuilder.builder().with(LOWERCASE, 0); //0 to keep old behavior
        if (includeUppercase) {
            builder.with(UPPERCASE, 1);
        }
        if (includeDigit) {
            builder.with(DIGITS, 1);
        }
        if (includeSpecial) {
            builder.with(DEFAULT_SPECIAL, 1);
        }
        return characters(fixedNumberOfCharacters, builder.build(), false);
    }

    private void shuffle(char[] buffer) {
        int length = buffer.length;
        for (int i = length; i > 0; i--) {
            int randInd = faker.random().nextInt(i);
            swap(buffer, randInd, i - 1);
        }
    }

    private void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    public List<String> words(int num) {
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            returnList.add(word());
        }
        return returnList;
    }

    public List<String> words() {
        return words(3);
    }

    public String word() {
        return faker.fakeValuesService().resolve("lorem.words", this, faker);
    }

    /**
     * Create a sentence with a random number of words within the range 4..10.
     *
     * @return a random sentence
     */
    public String sentence() {
        return sentence(3);
    }

    /**
     * Create a sentence with a random number of words within the range (wordCount+1)..(wordCount+6).
     *
     * @return a random sentence
     */
    public String sentence(int wordCount) {
        return sentence(wordCount, 6);
    }

    /**
     * Create a sentence with a random number of words within the range (wordCount+1)..(wordCount+randomWordsToAdd).
     * <p>
     * Set {@code randomWordsToAdd} to 0 to generate sentences with a fixed number of words.
     *
     * @return a random sentence
     */
    public String sentence(int wordCount, int randomWordsToAdd) {
        int numberOfWordsToAdd = randomWordsToAdd == 0 ? 0 : faker.random().nextInt(randomWordsToAdd);
        return capitalize(String.join(" ", words(wordCount + numberOfWordsToAdd)) + ".");
    }

    public List<String> sentences(int sentenceCount) {
        List<String> sentences = new ArrayList<>(sentenceCount);
        for (int i = 0; i < sentenceCount; i++) {
            sentences.add(sentence());
        }
        return sentences;
    }

    /**
     * Creates a paragraph with a range (sentenceCount)...(&lt;sentenceCount+3)
     */
    public String paragraph(int sentenceCount) {
        return String.join(" ", sentences(sentenceCount + faker.random().nextInt(3)));
    }

    public String paragraph() {
        return paragraph(3);
    }

    public List<String> paragraphs(int paragraphCount) {
        List<String> paragraphs = new ArrayList<>(paragraphCount);
        for (int i = 0; i < paragraphCount; i++) {
            paragraphs.add(paragraph());
        }
        return paragraphs;
    }

    /**
     * Create a string with a fixed size. Can be useful for testing
     * validator based on length string for example
     *
     * @param numberOfLetters size of the expected String
     * @return a string with a fixed size
     */
    public String fixedString(int numberOfLetters) {
        if (numberOfLetters <= 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        while (builder.length() < numberOfLetters) {
            builder.append(sentence());
        }
        return builder.substring(0, numberOfLetters);
    }

    /**
     * Create a Lorem Ipsum sentence with fixed length.
     *
     * @param fixedLength size of the expected Lorem Ipsum sentence.
     * @return a string with a fixed size.
     * Return empty string if input size is 0 or negative.
     */
    public String maxLengthSentence(final int fixedLength) {
        if (fixedLength <= 0) {
            return "";
        }

        String sentence = this.sentence(fixedLength);
        String endOfSentence = sentence.substring(fixedLength - 1, fixedLength);
        while (" ".equals(endOfSentence)) {
            sentence = this.sentence(fixedLength);
            endOfSentence = sentence.substring(fixedLength - 1, fixedLength);
        }

        return sentence.substring(0, fixedLength);
    }
}

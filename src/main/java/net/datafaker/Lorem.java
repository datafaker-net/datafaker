package net.datafaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Lorem {
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

    public String characters(int minimumLength, int maximumLength, boolean includeUppercase) {
        if (minimumLength == maximumLength) {
            return characters(minimumLength, includeUppercase);
        } else {
            return characters(faker.random().nextInt(minimumLength, maximumLength), includeUppercase);
        }
    }

    public String characters(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeDigit) {
        if (minimumLength == maximumLength) {
            return characters(minimumLength, includeUppercase, includeDigit);
        } else {
            return characters(faker.random().nextInt(minimumLength, maximumLength), includeUppercase, includeDigit);
        }
    }

    public String characters(int fixedNumberOfCharacters) {
        return characters(fixedNumberOfCharacters, false);
    }

    public String characters(int fixedNumberOfCharacters, boolean includeUppercase) {
        return characters(fixedNumberOfCharacters, includeUppercase, true);
    }

    public String characters(int minimumLength, int maximumLength,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return characters(faker.random().nextInt(minimumLength, maximumLength),
            includeUppercase, includeSpecial, includeDigit);
    }


    public String characters(int fixedNumberOfCharacters, boolean includeUppercase, boolean includeDigit) {
        if (fixedNumberOfCharacters < 1) {
            return "";
        }
        char[] buffer = new char[fixedNumberOfCharacters];
        for (int i = 0; i < buffer.length; i++) {
            char randomCharacter;

            if (includeDigit) {
                randomCharacter = characters[faker.random().nextInt(characters.length)];
            } else {
                randomCharacter = letters[faker.random().nextInt(letters.length)];
            }

            if (includeUppercase && faker.bool().bool()) {
                randomCharacter = Character.toUpperCase(randomCharacter);
            }
            buffer[i] = randomCharacter;
        }
        return new String(buffer);
    }

    public String characters(int fixedNumberOfCharacters,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {

        if (fixedNumberOfCharacters < 1)
            return "";

        char[] buffer = new char[fixedNumberOfCharacters];
        char[] special = new char[]{'!', '@', '#', '$', '%', '^', '&', '*'};
        char[] number = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] all = new char[special.length + characters.length];
        System.arraycopy(special, 0, all, 0, special.length);
        System.arraycopy(characters, 0, all, special.length, characters.length);
        char[] specialAndLetter = new char[special.length + letters.length];
        System.arraycopy(special, 0, specialAndLetter, 0, special.length);
        System.arraycopy(letters, 0, specialAndLetter, special.length, letters.length);

        int cnt = 0;
        if (includeUppercase) {
            char theUpper = Character.toUpperCase(letters[faker.random().nextInt(letters.length)]);
            buffer[cnt++] = theUpper;

        }

        if (includeSpecial) {
            char theSpecial = special[faker.random().nextInt(special.length)];
            if (cnt > fixedNumberOfCharacters - 1) return "";
            buffer[cnt++] = theSpecial;
        }

        if (includeDigit) {
            char theNum = number[faker.random().nextInt(number.length)];
            if (cnt > fixedNumberOfCharacters - 1) return "";
            buffer[cnt++] = theNum;
        }


        for (int i = cnt; i < buffer.length; i++) {
            char randomCharacter;

            if (includeSpecial && !includeDigit) {
                randomCharacter = specialAndLetter[faker.random().nextInt(specialAndLetter.length)];
            } else if (!includeSpecial && includeDigit) {
                randomCharacter = characters[faker.random().nextInt(characters.length)];
            } else if (!includeSpecial) {
                randomCharacter = letters[faker.random().nextInt(letters.length)];
            } else {                                            //includeSpecial && includeDigit
                randomCharacter = all[faker.random().nextInt(all.length)];
            }

            if (includeUppercase && faker.bool().bool()) {
                randomCharacter = Character.toUpperCase(randomCharacter);
            }
            buffer[i] = randomCharacter;
        }

        shuffle(buffer);
        return String.valueOf(buffer);
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

    private static String capitalize(String input) {
        if (input == null) return null;
        return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1);
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

    static {
        StringBuilder builder = new StringBuilder(36);
        for (char character = 'a'; character <= 'z'; character++) {
            builder.append(character);
        }
        letters = builder.toString().toCharArray();
        for (char number = '0'; number <= '9'; number++) {
            builder.append(number);
        }
        characters = builder.toString().toCharArray();
    }

    private static final char[] letters;
    private static final char[] characters;

}

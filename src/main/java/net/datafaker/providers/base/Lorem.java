package net.datafaker.providers.base;

import net.datafaker.internal.helper.WordUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @since 0.8.0
 */
public class Lorem extends AbstractProvider<BaseProviders> {

    protected Lorem(BaseProviders faker) {
        super(faker);
    }

    public char character() {
        return character(false);
    }

    public char character(boolean includeUppercase) {
        return characters(includeUppercase ? 2 : 1, includeUppercase).charAt(0);
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
        return characters(minimumLength, maximumLength, includeUppercase, false);
    }

    public String characters(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeDigit) {
        return characters(minimumLength, maximumLength, includeUppercase, false, includeDigit);
    }

    public String characters(int fixedNumberOfCharacters) {
        return characters(fixedNumberOfCharacters, false);
    }

    public String characters(int fixedNumberOfCharacters, boolean includeUppercase) {
        return faker.text().text(fixedNumberOfCharacters, fixedNumberOfCharacters, includeUppercase);
    }

    public String characters(int minimumLength, int maximumLength,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return faker.text().text(minimumLength, maximumLength, includeUppercase, includeSpecial, includeDigit);
    }

    public String characters(int fixedNumberOfCharacters, boolean includeUppercase, boolean includeDigit) {
        return characters(fixedNumberOfCharacters, includeUppercase, false, includeDigit);
    }

    public String characters(int fixedNumberOfCharacters,
                             boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return faker.text().text(fixedNumberOfCharacters, fixedNumberOfCharacters, includeUppercase, includeSpecial, includeDigit);
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
        return resolve("lorem.words");
    }

    public String supplemental() {
        return resolve("lorem.supplemental");
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
        final int totalWordCount = wordCount + numberOfWordsToAdd;
        StringBuilder sb = new StringBuilder();
        if (totalWordCount > 0) {
            sb.append(WordUtils.capitalize(word()));
        }
        for (int i = 1; i < totalWordCount; i++) {
            sb.append(" ").append(word());
        }
        return sb.append(".").toString();
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

        String sentence = sentence(fixedLength);
        final char space = ' ';
        while (space == sentence.charAt(fixedLength - 1)) {
            sentence = sentence(fixedLength);
        }

        return sentence.substring(0, fixedLength);
    }
}

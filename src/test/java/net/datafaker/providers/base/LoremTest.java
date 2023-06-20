package net.datafaker.providers.base;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LoremTest extends BaseFakerTest<BaseFaker> {

    private final Lorem lorem = faker.lorem();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(lorem::word, "lorem.words"));
    }

    @Test
    void shouldCreateFixedLengthString() {
        assertThat(lorem.fixedString(10)).hasSize(10);
        assertThat(lorem.fixedString(50)).hasSize(50);
        assertThat(lorem.fixedString(0)).isEmpty();
        assertThat(lorem.fixedString(-1)).isEmpty();
    }

    @Test
    void testCharacter() {
        assertThat(String.valueOf(lorem.character())).matches("[a-z\\d]");
    }

    @Test
    void testCharacterIncludeUpperCase() {
        assertThat(String.valueOf(lorem.character(false))).matches("[a-z\\d]");
        assertThat(String.valueOf(lorem.character(true))).matches("[a-zA-Z\\d]");
    }

    @Test
    void testCharactersShouldIncludeMinAndMaxLenght() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            results.add(lorem.characters(1, 10));
        }

        final List<String> min = results.stream().filter(x -> x.length() == 1).collect(Collectors.toList());
        final List<String> max = results.stream().filter(x -> x.length() == 10).collect(Collectors.toList());

        assertThat(min).isNotEmpty();
        assertThat(max).isNotEmpty();
    }

    @Test
    void testCharacters() {
        assertThat(lorem.characters()).matches("[a-z\\d]{255}");
    }

    @Test
    void testCharactersIncludeUpperCase() {
        assertThat(lorem.characters(false)).matches("[a-z\\d]{255}");
        assertThat(lorem.characters(true)).matches("[a-zA-Z\\d]{255}");
    }

    @Test
    void testCharactersWithLength() {
        assertThat(lorem.characters(2)).matches("[a-z\\d]{2}");
        assertThat(lorem.characters(500)).matches("[a-z\\d]{500}");
        assertThat(lorem.characters(0)).isEmpty();
        assertThat(lorem.characters(-1)).isEmpty();
    }

    @Test
    void testCharactersWithLengthIncludeUppercase() {
        assertThat(lorem.characters(2, false)).matches("[a-z\\d]{2}");
        assertThat(lorem.characters(500, false)).matches("[a-z\\d]{500}");
        assertThat(lorem.characters(2, true)).matches("[a-zA-Z\\d]{2}");
        assertThat(lorem.characters(500, true)).matches("[a-zA-Z\\d]{500}");
        assertThat(lorem.characters(0, false)).isEmpty();
        assertThat(lorem.characters(-1, true)).isEmpty();
    }

    @Test
    void testCharactersMinimumMaximumLength() {
        assertThat(lorem.characters(1, 10)).matches("[a-z\\d]{1,10}");
    }

    @RepeatedTest(10)
    void testCharactersMinimumMaximumLengthEquals() {
        assertThat(lorem.characters(5, 5)).matches("[a-z\\d]{5}");
    }

    @RepeatedTest(10)
    void testCharactersMinimumMaximumLengthEqualsIncludingUppercaseAndIncludingDigit() {
        assertThat(lorem.characters(6, 10, true, true)).matches("[a-zA-Z\\d]{6,10}");
    }

    @RepeatedTest(10)
    void testCharactersFixedLengthIncludingUppercaseAndIncludingDigit() {
        assertThat(lorem.characters(10, true, true)).matches("[a-zA-Z\\d]{10}");
    }

    @Test
    void testFixedNumberOfCharactersEmpty() {
        assertThat(lorem.characters(-1)).isEmpty();
        assertThat(lorem.characters(0)).isEmpty();

        assertThat(lorem.characters(-1, true, true, true)).isEmpty();
        assertThat(lorem.characters(0, false, false, false)).isEmpty();
    }


    @Test
    void testCharactersMinimumMaximumLengthIncludeUppercase() {
        assertThat(lorem.characters(1, 10, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void testCharactersMinimumMaximumLengthIncludeUppercaseIncludeDigit() {
        assertThat(lorem.characters(1, 10, false, false)).matches("[a-zA-Z]{1,10}");
        assertThat(lorem.characters(2, 10, true, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void testSentence() {
        String sentence = lorem.sentence();
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(3, 9);
        assertThat(sentence).endsWith(".");
    }

    @Test
    void testSentenceWithWordCount() {
        String sentence = lorem.sentence(10);
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(9, 15);
        assertThat(sentence).endsWith(".");
    }

    @RepeatedTest(10)
    void testSentenceWithWordCountAndRandomWordsToAdd() {
        assertThat(lorem.sentence(10, 10)).matches("(\\w+\\s?){10,20}\\.");
    }

    @RepeatedTest(10)
    void testSentenceFixedNumberOfWords() {
        assertThat(lorem.sentence(10, 0)).matches("(\\w+\\s?){10}\\.");
    }

    @Test
    void testWords() {
        assertThat(lorem.words()).isNotEmpty();
    }

    @RepeatedTest(10)
    void testMaxLengthSentence() {
        Random rand = new Random();
        // Test different lengths over 10 runs
        int length = Math.abs(rand.nextInt(10000));
        String s = lorem.maxLengthSentence(length);
        assertThat(s).hasSize(length);
    }

    @Test
    void testMaxLengthWithEmptySentence() {
        String s = lorem.maxLengthSentence(0);
        assertThat(s).isEmpty();
    }

    @Test
    void testMaxLengthWithNegativeLengthSentence() {
        String s = lorem.maxLengthSentence(-1);
        assertThat(s).isEmpty();
    }

    @RepeatedTest(10)
    void testSentences() {
        String paragraph = lorem.paragraph();
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(3, 6);
    }

    @RepeatedTest(10)
    void testSentencesWithCount() {
        String paragraph = lorem.paragraph(1);
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(1, 3);
    }
}

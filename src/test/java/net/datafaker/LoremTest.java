package net.datafaker;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoremTest extends AbstractFakerTest {

    @Test
    public void shouldCreateFixedLengthString() {
        assertEquals(10, faker.lorem().fixedString(10).length());
        assertEquals(50, faker.lorem().fixedString(50).length());
        assertEquals(0, faker.lorem().fixedString(0).length());
        assertEquals(0, faker.lorem().fixedString(-1).length());
    }

    @Test
    public void wordShouldNotBeNullOrEmpty() {
        assertThat(faker.lorem().word()).isNotEmpty();
    }

    @Test
    public void testCharacter() {
        assertThat(String.valueOf(faker.lorem().character())).matches("[a-z\\d]{1}");
    }

    @Test
    public void testCharacterIncludeUpperCase() {
        assertThat(String.valueOf(faker.lorem().character(false))).matches("[a-z\\d]{1}");
        assertThat(String.valueOf(faker.lorem().character(true))).matches("[a-zA-Z\\d]{1}");
    }

    @Test
    public void testCharactersShouldIncludeMinAndMaxLenght() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            results.add(faker.lorem().characters(1, 10));
        }

        final List<String> min = results.stream().filter(x -> x.length() == 1).collect(Collectors.toList());
        final List<String> max = results.stream().filter(x -> x.length() == 10).collect(Collectors.toList());

        assertTrue(min.size() > 0);
        assertTrue(max.size() > 0);
    }

    @Test
    public void testCharacters() {
        assertThat(faker.lorem().characters()).matches("[a-z\\d]{255}");
    }

    @Test
    public void testCharactersIncludeUpperCase() {
        assertThat(faker.lorem().characters(false)).matches("[a-z\\d]{255}");
        assertThat(faker.lorem().characters(true)).matches("[a-zA-Z\\d]{255}");
    }

    @Test
    public void testCharactersWithLength() {
        assertThat(faker.lorem().characters(2)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(0)).isEmpty();
        assertThat(faker.lorem().characters(-1)).isEmpty();
    }

    @Test
    public void testCharactersWithLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(2, false)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500, false)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(2, true)).matches("[a-zA-Z\\d]{2}");
        assertThat(faker.lorem().characters(500, true)).matches("[a-zA-Z\\d]{500}");
        assertThat(faker.lorem().characters(0, false)).isEmpty();
        assertThat(faker.lorem().characters(-1, true)).isEmpty();
    }

    @Test
    public void testCharactersMinimumMaximumLength() {
        assertThat(faker.lorem().characters(1, 10)).matches("[a-z\\d]{1,10}");
    }

    @RepeatedTest(10)
    public void testCharactersMinimumMaximumLengthEquals() {
        assertThat(faker.lorem().characters(5, 5)).matches("[a-z\\d]{5}");
    }

    @RepeatedTest(10)
    public void testCharactersMinimumMaximumLengthEqualsIncludingUppercaseAndIncludingDigit() {
        assertThat(faker.lorem().characters(8, 8, true, true)).matches("[a-zA-Z\\d]{8}");
    }

    @Test
    public void testFixedNumberOfCharactersEmpty() {
        assertEquals("", faker.lorem().characters(-1));
        assertEquals("", faker.lorem().characters(0));

        assertEquals("", faker.lorem().characters(-1, true, true, true));
        assertEquals("", faker.lorem().characters(0, false, false, false));
    }


    @Test
    public void testCharactersMinimumMaximumLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(1, 10, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    public void testCharactersMinimumMaximumLengthIncludeUppercaseIncludeDigit() {
        assertThat(faker.lorem().characters(1, 10, false, false)).matches("[a-zA-Z]{1,10}");
        assertThat(faker.lorem().characters(1, 10, true, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    public void testSentence() {
        String sentence = faker.lorem().sentence();
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(3, 9);
        assertThat(sentence).endsWith(".");
    }

    @Test
    public void testSentenceWithWordCount() {
        String sentence = faker.lorem().sentence(10);
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(9, 15);
        assertThat(sentence).endsWith(".");
    }

    @RepeatedTest(10)
    public void testSentenceWithWordCountAndRandomWordsToAdd() {
        assertThat(faker.lorem().sentence(10, 10)).matches("(\\w+\\s?){10,20}\\.");
    }

    @RepeatedTest(10)
    public void testSentenceFixedNumberOfWords() {
        assertThat(faker.lorem().sentence(10, 0)).matches("(\\w+\\s?){10}\\.");
    }

    @Test
    public void testWords() {
        assertThat(faker.lorem().words().size()).isGreaterThanOrEqualTo(1);
    }

    @RepeatedTest(10)
    public void testMaxLengthSentence() {
        Random rand = new Random();
        // Test different lengths over 10 runs
        int length = Math.abs(rand.nextInt(10000));
        String s = faker.lorem().maxLengthSentence(length);
        assertEquals(s.length(), length);
    }

    @Test
    public void testMaxLengthWithEmptySentence() {
        String s = faker.lorem().maxLengthSentence(0);
        assertEquals(s.length(), 0);
    }

    @Test
    public void testMaxLengthWithNegativeLengthSentence() {
        String s = faker.lorem().maxLengthSentence(-1);
        assertEquals(s.length(), 0);
    }

    @RepeatedTest(10)
    public void testSentences() {
        String paragraph = faker.lorem().paragraph();
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(3, 6);
    }

    @RepeatedTest(10)
    public void testSentencesWithCount() {
        String paragraph = faker.lorem().paragraph(1);
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(1, 3);
    }
}

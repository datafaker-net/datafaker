package net.datafaker.providers.base;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LoremTest extends BaseFakerTest<BaseFaker> {

    @Test
    void shouldCreateFixedLengthString() {
        assertThat(faker.lorem().fixedString(10)).hasSize(10);
        assertThat(faker.lorem().fixedString(50)).hasSize(50);
        assertThat(faker.lorem().fixedString(0)).isEmpty();
        assertThat(faker.lorem().fixedString(-1)).isEmpty();
    }

    @Test
    void wordShouldNotBeNullOrEmpty() {
        assertThat(faker.lorem().word()).isNotEmpty();
    }

    @Test
    void character() {
        assertThat(String.valueOf(faker.lorem().character())).matches("[a-z\\d]{1}");
    }

    @Test
    void characterIncludeUpperCase() {
        assertThat(String.valueOf(faker.lorem().character(false))).matches("[a-z\\d]{1}");
        assertThat(String.valueOf(faker.lorem().character(true))).matches("[a-zA-Z\\d]{1}");
    }

    @Test
    void charactersShouldIncludeMinAndMaxLenght() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            results.add(faker.lorem().characters(1, 10));
        }

        final List<String> min = results.stream().filter(x -> x.length() == 1).collect(Collectors.toList());
        final List<String> max = results.stream().filter(x -> x.length() == 10).collect(Collectors.toList());

        assertThat(min).isNotEmpty();
        assertThat(max).isNotEmpty();
    }

    @Test
    void characters() {
        assertThat(faker.lorem().characters()).matches("[a-z\\d]{255}");
    }

    @Test
    void charactersIncludeUpperCase() {
        assertThat(faker.lorem().characters(false)).matches("[a-z\\d]{255}");
        assertThat(faker.lorem().characters(true)).matches("[a-zA-Z\\d]{255}");
    }

    @Test
    void charactersWithLength() {
        assertThat(faker.lorem().characters(2)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(0)).isEmpty();
        assertThat(faker.lorem().characters(-1)).isEmpty();
    }

    @Test
    void charactersWithLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(2, false)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500, false)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(2, true)).matches("[a-zA-Z\\d]{2}");
        assertThat(faker.lorem().characters(500, true)).matches("[a-zA-Z\\d]{500}");
        assertThat(faker.lorem().characters(0, false)).isEmpty();
        assertThat(faker.lorem().characters(-1, true)).isEmpty();
    }

    @Test
    void charactersMinimumMaximumLength() {
        assertThat(faker.lorem().characters(1, 10)).matches("[a-z\\d]{1,10}");
    }

    @RepeatedTest(10)
    void charactersMinimumMaximumLengthEquals() {
        assertThat(faker.lorem().characters(5, 5)).matches("[a-z\\d]{5}");
    }

    @RepeatedTest(10)
    void charactersMinimumMaximumLengthEqualsIncludingUppercaseAndIncludingDigit() {
        assertThat(faker.lorem().characters(8, 8, true, true)).matches("[a-zA-Z\\d]{8}");
    }

    @Test
    void fixedNumberOfCharactersEmpty() {
        assertThat(faker.lorem().characters(-1)).isEmpty();
        assertThat(faker.lorem().characters(0)).isEmpty();

        assertThat(faker.lorem().characters(-1, true, true, true)).isEmpty();
        assertThat(faker.lorem().characters(0, false, false, false)).isEmpty();
    }


    @Test
    void charactersMinimumMaximumLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(1, 10, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void charactersMinimumMaximumLengthIncludeUppercaseIncludeDigit() {
        assertThat(faker.lorem().characters(1, 10, false, false)).matches("[a-zA-Z]{1,10}");
        assertThat(faker.lorem().characters(2, 10, true, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void sentence() {
        String sentence = faker.lorem().sentence();
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(3, 9);
        assertThat(sentence).endsWith(".");
    }

    @Test
    void sentenceWithWordCount() {
        String sentence = faker.lorem().sentence(10);
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(9, 15);
        assertThat(sentence).endsWith(".");
    }

    @RepeatedTest(10)
    void sentenceWithWordCountAndRandomWordsToAdd() {
        assertThat(faker.lorem().sentence(10, 10)).matches("(\\w+\\s?){10,20}\\.");
    }

    @RepeatedTest(10)
    void sentenceFixedNumberOfWords() {
        assertThat(faker.lorem().sentence(10, 0)).matches("(\\w+\\s?){10}\\.");
    }

    @Test
    void words() {
        assertThat(faker.lorem().words()).isNotEmpty();
    }

    @RepeatedTest(10)
    void maxLengthSentence() {
        Random rand = new Random();
        // Test different lengths over 10 runs
        int length = Math.abs(rand.nextInt(10000));
        String s = faker.lorem().maxLengthSentence(length);
        assertThat(s).hasSize(length);
    }

    @Test
    void maxLengthWithEmptySentence() {
        String s = faker.lorem().maxLengthSentence(0);
        assertThat(s).isEmpty();
    }

    @Test
    void maxLengthWithNegativeLengthSentence() {
        String s = faker.lorem().maxLengthSentence(-1);
        assertThat(s).isEmpty();
    }

    @RepeatedTest(10)
    void sentences() {
        String paragraph = faker.lorem().paragraph();
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(3, 6);
    }

    @RepeatedTest(10)
    void sentencesWithCount() {
        String paragraph = faker.lorem().paragraph(1);
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(1, 3);
    }
}

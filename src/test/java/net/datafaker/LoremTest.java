package net.datafaker;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static net.datafaker.Lorem.DIGITS;
import static net.datafaker.Lorem.LOWERCASE;
import static net.datafaker.Lorem.UPPERCASE;
import static org.assertj.core.api.Assertions.assertThat;

class LoremTest extends AbstractFakerTest {

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
    void testCharacter() {
        assertThat(String.valueOf(faker.lorem().character())).matches("[a-z\\d]{1}");
    }

    @Test
    void testCharacterIncludeUpperCase() {
        assertThat(String.valueOf(faker.lorem().character(false))).matches("[a-z\\d]{1}");
        assertThat(String.valueOf(faker.lorem().character(true))).matches("[a-zA-Z\\d]{1}");
    }

    @Test
    void testCharactersShouldIncludeMinAndMaxLenght() {
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
    void testCharacters() {
        assertThat(faker.lorem().characters()).matches("[a-z\\d]{255}");
    }

    @Test
    void testCharactersIncludeUpperCase() {
        assertThat(faker.lorem().characters(false)).matches("[a-z\\d]{255}");
        assertThat(faker.lorem().characters(true)).matches("[a-zA-Z\\d]{255}");
    }

    @Test
    void testCharactersWithLength() {
        assertThat(faker.lorem().characters(2)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(0)).isEmpty();
        assertThat(faker.lorem().characters(-1)).isEmpty();
    }

    @Test
    void testCharactersWithLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(2, false)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500, false)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(2, true)).matches("[a-zA-Z\\d]{2}");
        assertThat(faker.lorem().characters(500, true)).matches("[a-zA-Z\\d]{500}");
        assertThat(faker.lorem().characters(0, false)).isEmpty();
        assertThat(faker.lorem().characters(-1, true)).isEmpty();
    }

    @Test
    void testCharactersMinimumMaximumLength() {
        assertThat(faker.lorem().characters(1, 10)).matches("[a-z\\d]{1,10}");
    }

    @RepeatedTest(10)
    void testCharactersMinimumMaximumLengthEquals() {
        assertThat(faker.lorem().characters(5, 5)).matches("[a-z\\d]{5}");
    }

    @RepeatedTest(10)
    void testCharactersMinimumMaximumLengthEqualsIncludingUppercaseAndIncludingDigit() {
        assertThat(faker.lorem().characters(8, 8, true, true)).matches("[a-zA-Z\\d]{8}");
    }

    @Test
    void testFixedNumberOfCharactersEmpty() {
        assertThat(faker.lorem().characters(-1)).isEmpty();
        assertThat(faker.lorem().characters(0)).isEmpty();

        assertThat(faker.lorem().characters(-1, true, true, true)).isEmpty();
        assertThat(faker.lorem().characters(0, false, false, false)).isEmpty();
    }


    @Test
    void testCharactersMinimumMaximumLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(1, 10, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void testCharactersMinimumMaximumLengthIncludeUppercaseIncludeDigit() {
        assertThat(faker.lorem().characters(1, 10, false, false)).matches("[a-zA-Z]{1,10}");
        assertThat(faker.lorem().characters(2, 10, true, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    void testSentence() {
        String sentence = faker.lorem().sentence();
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(3, 9);
        assertThat(sentence).endsWith(".");
    }

    @Test
    void testSentenceWithWordCount() {
        String sentence = faker.lorem().sentence(10);
        String[] words = sentence.split(" ");

        assertThat(words.length).isBetween(9, 15);
        assertThat(sentence).endsWith(".");
    }

    @RepeatedTest(10)
    void testSentenceWithWordCountAndRandomWordsToAdd() {
        assertThat(faker.lorem().sentence(10, 10)).matches("(\\w+\\s?){10,20}\\.");
    }

    @RepeatedTest(10)
    void testSentenceFixedNumberOfWords() {
        assertThat(faker.lorem().sentence(10, 0)).matches("(\\w+\\s?){10}\\.");
    }

    @Test
    void testWords() {
        assertThat(faker.lorem().words()).isNotEmpty();
    }

    @RepeatedTest(10)
    void testMaxLengthSentence() {
        Random rand = new Random();
        // Test different lengths over 10 runs
        int length = Math.abs(rand.nextInt(10000));
        String s = faker.lorem().maxLengthSentence(length);
        assertThat(s).hasSize(length);
    }

    @Test
    void testMaxLengthWithEmptySentence() {
        String s = faker.lorem().maxLengthSentence(0);
        assertThat(s).isEmpty();
    }

    @Test
    void testMaxLengthWithNegativeLengthSentence() {
        String s = faker.lorem().maxLengthSentence(-1);
        assertThat(s).isEmpty();
    }

    @RepeatedTest(10)
    void testSentences() {
        String paragraph = faker.lorem().paragraph();
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(3, 6);
    }

    @RepeatedTest(10)
    void testSentencesWithCount() {
        String paragraph = faker.lorem().paragraph(1);
        int matches = StringUtils.countMatches(paragraph, ".");
        assertThat(matches).isBetween(1, 3);
    }

    @Test
    void everyPasswordShouldContainLowerCaseUpperCaseAndDigit() {
        int count = 0;
        Map<String, Integer> passwordSymbolsMap = Lorem.PasswordSymbolsBuilder.builder()
            .with(LOWERCASE, 1)
            .with(UPPERCASE, 1)
            .with(DIGITS, 1).build();
        while (count++ < 10000) {
            String password = faker.lorem().characters(6, 10, passwordSymbolsMap, false);
            assertThat(password).is(new Condition<>(pw -> {
                    for (int i = 0; i < pw.length(); i++) {
                        if (Character.isLowerCase(pw.charAt(i))) {
                            return true;
                        }
                    }
                    return false;
                }, "contains lower case"))
                .is(new Condition<>(pw -> {
                    for (int i = 0; i < pw.length(); i++) {
                        if (Character.isUpperCase(pw.charAt(i))) {
                            return true;
                        }
                    }
                    return false;
                }, "contains upper case"))
                .is(new Condition<>(pw -> {
                    for (int i = 0; i < pw.length(); i++) {
                        if (Character.isDigit(pw.charAt(i))) {
                            return true;
                        }
                    }
                    return false;
                }, "contains upper case"));
        }
    }

    @Test
    void passwordShouldContain3RULowerCaseAnd5CustomSpecialSymbols() {
        final String ruLowerCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        final String customSpecialSymbols = "!@#$%^&*;'][{}";
        final int ruCnt = 3;
        final int specSmbCnt = 5;
        Map<String, Integer> passwordSymbolsMap = Lorem.PasswordSymbolsBuilder.builder()
            .with(ruLowerCase, ruCnt)
            .with(customSpecialSymbols, specSmbCnt).build();
        for (int i = 0; i < 100; i++) {
            final String pass = faker.lorem().characters(ruCnt + specSmbCnt, 10, passwordSymbolsMap, false);
            assertThat(pass).matches(s -> {
                int j = 0;
                int curRuCnt = 0;
                while (j < s.length() && curRuCnt < ruCnt) {
                    if (ruLowerCase.indexOf(s.charAt(j++)) >= 0) curRuCnt++;
                }
                return curRuCnt >= ruCnt;
            }).matches(s -> {
                int j = 0;
                int curSpecSmbCnt = 0;
                while (j < s.length() && curSpecSmbCnt < specSmbCnt) {
                    if (customSpecialSymbols.indexOf(s.charAt(j++)) >= 0) curSpecSmbCnt++;
                }
                return curSpecSmbCnt >= specSmbCnt;
            });
        }
    }

    @Test
    void exceptionIfLengthIsShorterThanNumberOfRequiredSymbols() {
        Map<String, Integer> passwordSymbolsMap = Lorem.PasswordSymbolsBuilder.builder()
            .with(LOWERCASE, 1)
            .with(UPPERCASE, 1)
            .with(DIGITS, 1).build();
        Assertions.assertThatThrownBy(() ->
                faker.lorem().characters(1, 1, passwordSymbolsMap, true))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

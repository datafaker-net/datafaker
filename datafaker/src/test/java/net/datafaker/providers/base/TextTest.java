package net.datafaker.providers.base;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static net.datafaker.providers.base.Text.DIGITS;
import static net.datafaker.providers.base.Text.EN_LOWERCASE;
import static net.datafaker.providers.base.Text.EN_UPPERCASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TextTest extends BaseFakerTest<BaseFaker> {

    @Test
    void textShouldContain3RULowerCaseAnd5CustomSpecialSymbols() {
        final String ruLowerCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        final String customSpecialSymbols = "!@#$%^&*;'][{}";
        final int ruCnt = 3;
        final int specSmbCnt = 5;
        final Text.TextRuleConfig config = Text.TextSymbolsBuilder.builder()
            .len(faker.number().numberBetween(ruCnt + specSmbCnt, Math.max(ruCnt + specSmbCnt, 10)))
            .with(ruLowerCase, ruCnt)
            .with(customSpecialSymbols, specSmbCnt).build();

        for (int i = 0; i < 10; i++) {
            final String text = faker.text().text(config);
            assertThat(text).matches(s -> {
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
        assertThatThrownBy(() ->
            faker.text().text(Text.TextSymbolsBuilder.builder()
                .len(1)
                .with(EN_LOWERCASE, 1)
                .with(EN_UPPERCASE, 1)
                .with(DIGITS, 1)
                .throwIfLengthSmall(true)
                .build()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void everyTextShouldContainLowerCaseUpperCaseAndDigit() {
        int count = 0;
        final Text.TextRuleConfig config = Text.TextSymbolsBuilder.builder()
            .len(faker.number().numberBetween(6, 10))
            .with(EN_LOWERCASE, 1)
            .with(EN_UPPERCASE, 1)
            .with(DIGITS, 1).build();
        while (count++ < 1000) {
            String text = faker.text().text(config);
            assertThat(text).is(new Condition<>(pw -> {
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
    void testCharacter() {
        final Pattern characterPattern = Pattern.compile("[A-Za-z]");
        for (int i = 0; i < 100; i++) {
            Character character = faker.text().character();
            assertThat(character.toString()).matches(characterPattern);
        }
    }

    @RepeatedTest((100))
    void testUppercaseCharacter() {
        Character character = faker.text().uppercaseCharacter();
        assertThat(character).isUpperCase();
    }

    @RepeatedTest((100))
    void testLowercaseCharacter() {
        Character character = faker.text().lowercaseCharacter();
        assertThat(character).isLowerCase();
    }

    @Test
    void testFixedLengthText() {
        for (int i = 0; i < 100; i++) {
            String text = faker.text().text(i);
            assertThat(text).hasSize(i);
        }
    }

    @Test
    void testDefaultLengthText() {
        for (int i = 0; i < 100; i++) {
            String text = faker.text().text();
            assertThat(text).hasSizeBetween(20, 80);
        }
    }

}

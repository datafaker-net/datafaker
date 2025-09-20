package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static net.datafaker.providers.base.Text.DEFAULT_SPECIAL;
import static net.datafaker.providers.base.Text.DIGITS;
import static net.datafaker.providers.base.Text.EN_LOWERCASE;
import static net.datafaker.providers.base.Text.EN_UPPERCASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TextTest {
    private static final Pattern characterPattern = Pattern.compile("[A-Za-z]");
    private final Faker faker = new Faker();

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
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Min length (1) should be not smaller than number of required characters (3)");
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

    @RepeatedTest(10)
    void testCharacter() {
        Character character = faker.text().character();
        assertThat(character.toString()).matches(characterPattern);
    }

    @RepeatedTest(10)
    void testUppercaseCharacter() {
        Character character = faker.text().uppercaseCharacter();
        assertThat(character).isUpperCase();
    }

    @RepeatedTest(10)
    void testLowercaseCharacter() {
        Character character = faker.text().lowercaseCharacter();
        assertThat(character).isLowerCase();
    }

    @Test
    void testFixedLengthText() {
        for (int i = 0; i < 10; i++) {
            String text = faker.text().text(i);
            assertThat(text).hasSize(i).matches("[a-z]*");
        }
    }

    @RepeatedTest(10)
    void testDefaultLengthText() {
        String text = faker.text().text();
        assertThat(text).hasSizeBetween(20, 80).matches("[a-z]{20,80}");
    }

    @RepeatedTest(10)
    void upTo64LowerCase() {
        assertThat(faker.text().text(1, 64, false, false, false)).matches("[a-z]{1,64}");
        assertThat(faker.text().text(2, 64, false, false, false)).matches("[a-z]{2,64}");
        assertThat(faker.text().text(64, 64, false, false, false)).matches("[a-z]{64}");
    }

    @Test
    void zeroLength() {
        assertThat(faker.text().text(0, 0, false, false, false)).isEqualTo("");
    }

    @Test
    void oneLowerCase() {
        assertThat(faker.text().text(1, 1, false, false, false)).matches("[a-z]");
        assertThat(faker.text().text(0, 1, false, false, false)).matches("[a-z]?");
    }

    @RepeatedTest(10)
    void oneWithUpperCase() {
        assertThat(faker.text().text(1, 1, true, false, false)).matches("[A-Z]");
    }

    @RepeatedTest(10)
    void oneWithDigit() {
        assertThat(faker.text().text(1, 1, false, false, true)).matches("[0-9]");
    }

    @RepeatedTest(10)
    void oneWithSpecialSymbol() {
        assertThat(faker.text().text(1, 1, false, true, false)).matches("[" + DEFAULT_SPECIAL + "]");
    }

    @RepeatedTest(10)
    void twoWithUpperCaseAndDigit() {
        assertThat(faker.text().text(2, 2, true, false, true)).matches("[A-Z0-9]{2}");
    }

    @RepeatedTest(10)
    void twoWithLowerAndUpperCaseAndDigit() {
        assertThat(faker.text().text(2, 2, true, false, true)).matches("[a-zA-Z0-9]{2}");
        assertThat(faker.text().text(3, 3, true, true, true)).matches("[a-zA-Z0-9" + DEFAULT_SPECIAL + "]{3}");
    }

    @Test
    void minLengthCannotBeGreaterThanMaxLength() {
        assertThatThrownBy(() -> faker.text().text(22, 21, false, false, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Min length (22) should not be greater than max length (21)");

        assertThatThrownBy(() -> faker.text().text(3, 2, true, true, true))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Min length (3) should not be greater than max length (2)");
    }

    @Test
    void isNotEnoughLengthToContainAllRequiredSymbols() {
        assertThatThrownBy(() -> faker.text().text(0, 0, true, false, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Minimum number of required characters (1) should not be greater than max length (0)");
        assertThatThrownBy(() -> faker.text().text(1, 2, true, true, true))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Minimum number of required characters (3) should not be greater than max length (2)");
    }

    @RepeatedTest(10)
    void minimumLengthIsNotEnoughToContainAllRequiredSymbols() {
        assertThat(faker.text().text(1, 4, true, false, true)).matches("[a-zA-Z0-9]{2,4}");
    }
}

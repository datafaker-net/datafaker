package net.datafaker.base;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static net.datafaker.base.Password.DIGITS;
import static net.datafaker.base.Password.EN_LOWERCASE;
import static net.datafaker.base.Password.EN_UPPERCASE;
import static org.assertj.core.api.Assertions.assertThat;

class PasswordTest extends BaseFakerTest<BaseFaker> {
    @Test
    void passwordShouldContain3RULowerCaseAnd5CustomSpecialSymbols() {
        final String ruLowerCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        final String customSpecialSymbols = "!@#$%^&*;'][{}";
        final int ruCnt = 3;
        final int specSmbCnt = 5;
        final Password.PasswordRuleConfig config = Password.PasswordSymbolsBuilder.builder()
            .withMinLength(ruCnt + specSmbCnt)
            .withMaxLength(Math.max(ruCnt + specSmbCnt, 10))
            .with(ruLowerCase, ruCnt)
            .with(customSpecialSymbols, specSmbCnt).build(faker);
        for (int i = 0; i < 1000; i++) {
            final String pass = faker.password().password(config);
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
        Assertions.assertThatThrownBy(() ->
                faker.password().password(Password.PasswordSymbolsBuilder.builder()
                    .withMinLength(1)
                    .withMaxLength(1)
                    .with(EN_LOWERCASE, 1)
                    .with(EN_UPPERCASE, 1)
                    .with(DIGITS, 1)
                    .throwIfLengthSmall(true)
                    .build(faker)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void everyPasswordShouldContainLowerCaseUpperCaseAndDigit() {
        int count = 0;
        final Password.PasswordRuleConfig config = Password.PasswordSymbolsBuilder.builder()
            .withMinLength(6)
            .withMaxLength(10)
            .with(EN_LOWERCASE, 1)
            .with(EN_UPPERCASE, 1)
            .with(DIGITS, 1).build(faker);
        while (count++ < 1000) {
            String password = faker.password().password(config);
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

}

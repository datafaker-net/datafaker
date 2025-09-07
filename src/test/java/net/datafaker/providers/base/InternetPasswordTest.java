package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class InternetPasswordTest {
    private final Faker faker = new Faker();

    @Test
    @SuppressWarnings("removal")
    void testPassword1000() {
        final Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
        final Pattern digitPattern = Pattern.compile("[0-9]");
        for (int i = 0; i < 1000; i++) {
            String password = faker.internet().password(8, 16, true, true, true);
            Matcher specialCharacterMatcher = specialCharacterPattern.matcher(password);
            Matcher digitMatcher = digitPattern.matcher(password);

            boolean isPasswordContainsSpecialCharacter = specialCharacterMatcher.find();
            boolean isPasswordContainsDigit = digitMatcher.find();

            assertThat(isPasswordContainsDigit).isTrue();
            assertThat(isPasswordContainsSpecialCharacter).isTrue();
        }
    }

    @Test
    @SuppressWarnings("removal")
    void passwordSpecial() {
        boolean check = true;
        for (int i = 0; i < 10; i++) {
            String password = faker.internet().password(8, 16, true, true, true);
            Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher specialCharacterMatcher = specialCharacterPattern.matcher(password);
            if (!specialCharacterMatcher.find()) {
                check = false;
                break;
            }

        }
        assertThat(check).isTrue();
    }

    @Test
    @SuppressWarnings("removal")
    void passwordMix() {
        boolean check = true;
        for (int i = 0; i < 10; i++) {
            String password = faker.internet().password(8, 16, true, true, true);
            Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher specialCharacterMatcher = specialCharacterPattern.matcher(password);
            Pattern digitPattern = Pattern.compile("[0-9]");
            Matcher digitMatcher = digitPattern.matcher(password);
            if (!specialCharacterMatcher.find()) {
                check = false;
                break;
            }
            if (!digitMatcher.find()) {
                check = false;
                break;
            }
        }
        assertThat(check).isTrue();
    }
}

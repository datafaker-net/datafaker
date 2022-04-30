package net.datafaker;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InternetPasswordTest extends AbstractFakerTest {
    @Test
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

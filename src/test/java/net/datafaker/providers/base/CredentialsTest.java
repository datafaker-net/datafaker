package net.datafaker.providers.base;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CredentialsTest {
    private final BaseFaker faker = new BaseFaker();

    @RepeatedTest(100)
    void testUsername() {
        assertThat(faker.credentials().username()).matches("^(\\w+)\\.(\\w+)$");
    }

    @ParameterizedTest
    @MethodSource("userNameWithSpacesProvider")
    @SuppressWarnings("removal")
    void testUsernameWithSpaces(String firstName, String lastName, String expected) {
        Locale locale = new Locale("TR");
        Name name = mock();
        doReturn(firstName).when(name).firstName();
        doReturn(lastName).when(name).lastName();

        BaseFaker mockedFaker = new BaseFaker(locale) {
            @Override
            public Name name() {
                return name;
            }
        };

        assertThat(mockedFaker.internet().username())
            .isEqualTo(expected);
    }

    private static Stream<Arguments> userNameWithSpacesProvider() {
        return Stream.of(
            Arguments.of("Jin Quan", "D'Artagnan", "jinquan.dartagnan"),
            Arguments.of("Ivan Ivanov", "Другой Язык", "ıvanıvanov.другойязык")
        );
    }

    @RepeatedTest(100)
    void testPassword() {
        assertThat(faker.credentials().password()).hasSizeBetween(8, 16).matches("^[a-z0-9]+$");
    }

    @RepeatedTest(100)
    void testPasswordWithoutDigits() {
        assertThat(faker.credentials().password(false)).hasSizeBetween(8, 16).matches("^[a-z]+$");
    }

    @RepeatedTest(100)
    void testPasswordWithSpecificSize() {
        assertThat(faker.credentials().password(3, 7)).hasSizeBetween(3, 7).matches("^[a-z0-9]+$");
    }

    @RepeatedTest(100)
    void testPasswordWithSpecificSizeAndUppercase() {
        assertThat(faker.credentials().password(8, 16, true)).hasSizeBetween(8, 16).matches("^[a-zA-Z0-9]+$");
    }

    @Test
    void testPassword1000() {
        final Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
        final Pattern digitPattern = Pattern.compile("[0-9]");
        for (int i = 0; i < 1000; i++) {
            String password = faker.credentials().password(8, 16, true, true, true);
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
            String password = faker.credentials().password(8, 16, true, true, true);
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
            String password = faker.credentials().password(8, 16, true, true, true);
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

    @Test
    void weakPassword() {
        Object obj = faker.fakeValuesService().fetchObject("credentials.weak_password", faker.getContext());
        final List<String> list = (obj instanceof List<?> l
                && l.stream().allMatch(String.class::isInstance))
                        ? l.stream().map(String.class::cast).toList()
                        : Collections.emptyList();

        assertThat(faker.credentials().weakPassword()).isIn(list);
    }

    @RepeatedTest(100)
    void testUserId() {
        String uid = faker.credentials().userId();
        assertThat(uid).matches("^([A-Z]{1})?[0-9]{5,6}$");
    }

    @Test
    void userIdWithParameter() {
        String uid = faker.credentials().userId(null);
        assertThat(uid).isNull();

        uid = faker.credentials().userId("*");
        assertThat(uid).isNull();

        uid = faker.credentials().userId("");
        assertThat(uid).isEmpty();
    }
}

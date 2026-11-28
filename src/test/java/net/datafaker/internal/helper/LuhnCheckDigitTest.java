package net.datafaker.internal.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

final class LuhnCheckDigitTest {

    @DisplayName("Private constructor should throw UnsupportedOperationException")
    @Test
    void privateConstructor_shouldThrowException() {
        assertConstructorIsPrivate(LuhnCheckDigit.class);
    }

    @DisplayName("Verify Luhn check digit calculation from CharSequence inputs")
    @ParameterizedTest(name = "[{index}] Sequence: ''{0}'' -> Expected Digit: {1}")
    @CsvSource({
        // Standard credit card numbers / IMF examples (Base sequence without the check digit)
        "4992739871, 6",
        // test ignoring non-digits (dashes, spaces)
        "4992-7398-71, 6",
        "4992 7398 71, 6",
        // IMEI examples
        "49015420323751, 8",
        // single digit edge cases
        "0, 0",
        "1, 8",
        "9, 1"
    })
    void shouldCalculateCorrectDigitForCharSequence(CharSequence sequence, int expectedCheckDigit) {
        int actualDigit = LuhnCheckDigit.calculate(sequence);
        assertThat(actualDigit)
            .as("Luhn check digit for sequence '%s' should be %d", sequence, expectedCheckDigit)
            .isEqualTo(expectedCheckDigit);
    }

    @DisplayName("Verify Luhn check digit calculation from primitive int array inputs")
    @ParameterizedTest(name = "[{index}] Array length: {1} -> Expected Digit: {2}")
    @MethodSource("intArrayProvider")
    void shouldCalculateCorrectDigitForIntArray(int[] digits, String description, int expectedCheckDigit) {
        int actualDigit = LuhnCheckDigit.calculate(digits);
        assertThat(actualDigit)
            .as("Luhn check digit for %s should be %d", description, expectedCheckDigit)
            .isEqualTo(expectedCheckDigit);
    }

    @DisplayName("Verify that null safe guard returns 0 for CharSequence method")
    @ParameterizedTest
    @NullSource
    void shouldReturnZeroOnNullInput(CharSequence nullSequence) {
        assertThat(LuhnCheckDigit.calculate(nullSequence)).isZero();
    }

    private static Stream<Arguments> intArrayProvider() {
        return Stream.of(
            Arguments.of(new int[]{4, 9, 9, 2, 7, 3, 9, 8, 7, 1}, "Standard CC digits", 6),
            Arguments.of(new int[]{4, 9, 0, 1, 5, 4, 2, 0, 3, 2, 3, 7, 5, 1}, "IMEI digits", 8),
            Arguments.of(new int[]{0}, "Single zero digit", 0),
            Arguments.of(new int[]{1}, "Single one digit", 8),
            Arguments.of(new int[]{9}, "Single nine digit", 1)
        );
    }

    /**
     * Asserts that a class has a private default constructor and throwing an
     * {@link UnsupportedOperationException} on instantiation.
     *
     * @param <T>   the type of the class
     * @param clazz the class to be tested
     * @return the private constructor
     * @throws AssertionError if any assertion fails
     */
    public static <T> Constructor<T> assertConstructorIsPrivate(Class<T> clazz) {
        assertThat(clazz).isNotNull();

        Constructor<T> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new AssertionError("Private default constructor not found for " + clazz.getSimpleName(), ex);
        }

        assertThat(Modifier.isPrivate(constructor.getModifiers()))
            .as("Constructor of utility class must be private")
            .isTrue();

        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
            .isInstanceOf(InvocationTargetException.class)
            .hasCauseInstanceOf(UnsupportedOperationException.class)
            .hasStackTraceContaining("cannot be instantiated");

        return constructor;
    }

}

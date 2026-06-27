package net.datafaker.internal.helper;

/**
 * Calculates the check digit using the Luhn algorithm (Modulus 10).
 *
 * @since 2.8.0
 */
public final class LuhnCheckDigit {

    private LuhnCheckDigit() {
        throw new UnsupportedOperationException(
            String.format("Utility class %s cannot be instantiated", getClass().getSimpleName()));
    }

    /**
     * Extracts all digits from a CharSequence and calculates the Luhn check digit.
     * <p>
     * Non-digit characters are automatically ignored.
     *
     * @param sequence the sequence containing the numeric characters
     * @return the single check digit (0-9)
     */
    public static int calculate(CharSequence sequence) {
        if (sequence == null) {
            return 0;
        }
        // avoid boxing overhead by using codePoints() or chars() directly to int[]
        int[] digits = sequence.chars()
            .filter(Character::isDigit)
            .map(c -> c - '0')
            .toArray();

        return calculate(digits);
    }

    /**
     * Calculates the Luhn check digit for a given array of numeric values.
     * <p>
     * The algorithm processes the array from right to left, doubling every second digit
     * starting with the rightmost element.
     *
     * @param digits an array of numbers representing the base value
     * @return the single check digit (0-9) that satisfies the Luhn criteria
     */
    public static int calculate(int[] digits) {
        int luhnSum = 0;
        int multiplier = 2;

        for (int i = digits.length - 1; i >= 0; i--) {
            int product = digits[i] * multiplier;
            luhnSum += (product / 10) + (product % 10);
            multiplier = (multiplier == 2 ? 1 : 2);
        }

        return (10 - (luhnSum % 10)) % 10;
    }

}
